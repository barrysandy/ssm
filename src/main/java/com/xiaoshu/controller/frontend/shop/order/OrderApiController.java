package com.xiaoshu.controller.frontend.shop.order;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.*;
import com.xiaoshu.tools.sendMsg.EnumsTemplateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * 订单API模块
 * @name: OrderApiController
 * @author: XGB
 * @date: 2018-04-13 15:11
 */
@Controller
@RequestMapping(value = "/order")
public class OrderApiController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(OrderApiController.class);

	@Resource private WaterBillService waterBillService;
	@Resource private CommodityService commodityService;
	@Resource private OrderService orderService;
	@Resource private MessageRecordService messageRecordService;
	@Resource private CommodityGroupService commodityGroupService;
	@Resource private CommodityGroupMemberService commodityGroupMemberService;
	@Resource private CommodityPriceListService commodityPriceListService;

	/**
	 * 需要将该支付地址 配置到微信商户平台中
	 * http://www.daxi51.com/ssm/order/(千万不要加payOrder/)
	 * http://w17b197823.iok.la/ssm/order/(千万不要加payOrder/)
	 * Controller_Function ：发起订单付款
	 * Access URL ：order/payOrder?order_id=
	 * @param order_id 订单order_id
	 * @return
	 */
	@RequestMapping("payOrderInUser")
	public String payOrderInUser(HttpServletRequest req, HttpServletResponse resp,Integer order_id, String menuId){
		resp.setCharacterEncoding("UTF-8");
		try {
			Order order = orderService.getById(order_id);
			if(order != null){
				req.setAttribute("cid", order.getCommodityId());
			}
			req.setAttribute("order", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("menuId", menuId);
		return "mobile/mall/toPay";//支付页面
	}

	/**
	 * 获取订单支付状态
	 * Access URL ：order/getOrderTypeStatusNoUser?orderNo=
	 * @param orderNo 订单 orderNo
	 */
	@RequestMapping("getOrderTypeStatusNoUser")
	@ResponseBody
	public String getOrderTypeStatusNoUser(HttpServletResponse resp,String orderNo){
		try {
			int typeState = orderService.getTypeStateByOrderNo(orderNo);
			resp.getWriter().print(typeState);
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
		return null;
	}


	/**
	 *Controller_Function ：订单付款成功，修改订单状态
	 *Access URL ：order/payOrderCompleteInUser?order_no=
	 * @param order_no 订单order_no
	 * @return  (1) 1订单付款成功    (2) -1订单已经付款成功，不要重复付款 (3)0订单不存在
	 */
	@ResponseBody
	@RequestMapping("payOrderCompleteInUser")
	public String payOrderCompleteInUser(HttpServletRequest req, HttpServletResponse resp,@RequestParam("order_no") String order_no){
		FocusedUserInfo user = (FocusedUserInfo) req.getSession().getAttribute("user");
		String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);//当前时间
		/**
		 *  sendMsgType 控制短信发送的变量
		 *  0 不发送短信
		 *  1 单一商品购买（未满足开团人数）
		 *  2 单一商品购买（满足开团人数，群发开团短信）
		 *  3 单一商品购买（满足开团人数，合并1-2的短信）
		 *  （当前Controller中不会出现该情况）4 单一商品购买（未满足开团人数，时间到达，群发失败短信）
		 *
		 *  5 组团商品购买（当前团未满短信）
		 *  6 组团商品购买（当前团满群发组团成功短信）
		 *  7 组团商品购买（当前团满短信时，合并5-6短信）
		 *  （当前Controller中不会出现该情况）8 组团商品购买（未满足开团人数，时间到达，群发失败短信）
		 */
		Integer sendMsgType = 0;
		String groupId = null;
		try {
			//1.查询订单是否存在
			int exitOrder = orderService.countByOrderNo(order_no);
			if(exitOrder != 0){
				Order order = orderService.getByOrderNo(order_no);
				//2.订单是否为未付款
				int stateOrder = orderService.getTypeStateByOrderNo(order_no);
				//3.修改订单状态为支付状态
				if(stateOrder == 0){
					int i = orderService.updateOldTypeStateToOrderStateByOrderNo(order_no, 1,0,"");
					int exitWaterBill = waterBillService.existByOrderNo(order_no);
					System.out.println("------------------------------------");
					System.out.println("------------------------------------");
					log.info("------------ [LOG[" + nowTime + "] OrderController payOrderCompleteInUser] exitWaterBill result : " + exitWaterBill + " ------------");
					System.out.println("------------------------------------");
					System.out.println("------------------------------------");
					if(exitWaterBill == 0){
						WaterBill waterBill = new WaterBill(0, "Front-end callback add water", order_no, new Date(), null, order.getOrderAmountMoney(), 1, user.getOpenid(), user.getId());
						waterBillService.save(waterBill);
					}
					resp.getWriter().print(i);
				}else{
					//TODO 获取订单状态
					int typeState = orderService.getTypeStateByOrderNo(order_no);
					if(typeState == 1){
						resp.getWriter().print(0);
					}else{
						System.out.println("------------------------------------");
						log.info("------------ [LOG[" + nowTime + "] OrderController payOrderCompleteInUser] 0 : ------------");
						System.out.println("------------------------------------");
						resp.getWriter().print(0);
					}

				}

				//TODO 当前订单为单一商品类型订单
				if( order.getOrderType() == 1 ){
					Commodity bean = commodityService.findCommodityByIdPriceStatusOne(order.getCommodityId());
					int max = 0;//TODO 订单付款成功判断是否已经报满 max = 0 未满  max = 1 满员
					max = getMax(bean,orderService, max);
					if(max == 0){
						sendMsgType = 1;
					}else {
						sendMsgType = 3;
					}

				} else if( order.getOrderType() == 2 ){ //组团商品
					int max = 0;//TODO 判断是否已组团完成 max = 0 未成团  max = 1 成团
					//TODO 当前订单为组团类型订单
					//TODO 组团信息设置
					//组团的最大成员数
					Integer createGroupNumber = commodityService.getCreateGroupNumberById(order.getCommodityId());
					//查询当前购买者是否有团
					groupId = commodityGroupMemberService.getGroupIdById(order.getCommodityId(),user.getId());
					if(groupId != null){ //表示存在组团 更新组团信息
						CommodityGroup commodityGroup = commodityGroupService.getById(groupId);
						CommodityGroupMember commodityGroupMember = commodityGroupMemberService.getByUserIdAndCId(order.getCommodityId(),user.getId());
						if(commodityGroupMember != null){
							commodityGroupMemberService.updateStatusById(1,commodityGroupMember.getId());
						}else {
							commodityGroupMember = new CommodityGroupMember(UUID.randomUUID().toString(), order.getCommodityId(), new Date(), "", user.getId(), 1, order_no, groupId, 1);
							commodityGroupMemberService.save(commodityGroupMember);
						}

						//TODO
						//TODO 更新组团的总人数
						Integer total = commodityGroupMemberService.countByStatus(groupId,1);
						commodityGroupService.updateTotalPersonById(total,commodityGroup.getTotalPerson(),groupId);

					}else {
						String groupTime = null;
						CommodityPriceList commodityPriceList = commodityPriceListService.getByCommodityIdAndDesC(order.getCommodityId(),order.getUserUseTime());
						if(commodityPriceList != null) {
							groupTime = commodityPriceList.getPriceTime() + " 00:00:00";
						}
						//不存在组团 创建新组团
						groupId = UUID.randomUUID().toString();
						String groupCode = ToolsDate.getStringDate(ToolsDate.dtLongSecond) + ToolsString.generateRandNumber(4);
						CommodityGroup commodityGroup = new CommodityGroup(groupId,groupCode, order.getCommodityId(), new Date(),groupTime, "", 0 , createGroupNumber, 0);
						commodityGroupService.save(commodityGroup);
						//加入组团成员
						CommodityGroupMember commodityGroupMember = new CommodityGroupMember(UUID.randomUUID().toString(), order.getCommodityId(), new Date(), "", user.getId(), 1, order_no, groupId, 0);
						commodityGroupMemberService.save(commodityGroupMember);

						//TODO
						//TODO 更新组团的总人数
						Integer total = commodityGroupMemberService.countByStatus(groupId,1);
						commodityGroupService.updateTotalPersonById(total,commodityGroup.getTotalPerson(),groupId);
					}


					//TODO 判断当前团是否达到满员 更新改团的状态为组团成功 并向该团所有成员发送组团成功信息
					Integer currentTotal = commodityGroupMemberService.countByStatus(groupId,1);
					if(currentTotal >= createGroupNumber){
						commodityGroupService.updateStatusById(1,groupId );
						max = 1;
					}
					if(max == 0){
						sendMsgType = 5;
					}else {
						sendMsgType = 7;
					}

				}
				//TODO 发送短信
				//TODO 根据类型调用相应的接口发送短信
				/**
				 *  sendMsgType 控制短信发送的变量
				 *  0 不发送短信
				 *  1 单一商品购买（未满足开团人数）
				 *  （当前Controller中不会出现该情况）2 单一商品购买（满足开团人数，群发开团短信）
				 *  3 单一商品购买（满足开团人数，合并1-2的短信）
				 *  （当前Controller中不会出现该情况）4 单一商品购买（未满足开团人数，时间到达，群发失败短信）
				 *
				 *  5 组团商品购买（当前团未满短信）
				 *  （当前Controller中不会出现该情况）6 组团商品购买（当前团满群发组团成功短信）
				 *  7 组团商品购买（当前团满短信时，合并5-6短信）
				 *  （当前Controller中不会出现该情况）8 组团商品购买（未满足开团人数，时间到达，群发失败短信）
				 */

				if(sendMsgType == 1){
					messageRecordService.sendSingleBuyMsg(order.getId(), EnumsTemplateType.SINGLE_BUY);
				}else if(sendMsgType == 3){
					messageRecordService.sendSingleGroupSucBuyToBuyMsg(order.getId(),EnumsTemplateType.SINGLE_BUY_GROUP_SUC_TOBUY);
					messageRecordService.sendSingleGroupSucBuyMsg(order.getCommodityId(),EnumsTemplateType.SINGLE_BUY_GROUP_SUC_MASS);
				}else if(sendMsgType == 5){
					messageRecordService.sendGroupBuyMsg(order.getId(),EnumsTemplateType.GROUP_BUY,groupId);
				}else if(sendMsgType == 7){
					messageRecordService.sendGroupBuySucMassToBuyMsg(order.getId(),EnumsTemplateType.GROUP_BUY_SUC_TOBUY,groupId);
					messageRecordService.sendGroupBuySucMassMsg(order.getCommodityId(),EnumsTemplateType.GROUP_BUY_SUC_MASS,groupId);
				}

			}else{
				resp.getWriter().print(-1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}



		return null;
	}



	/**
	 * 更新订单的联系人信息
	 * Access URL ：order/updateContactsNoUser?order_no=&userName=&userPhone=&userUserTime=
	 * @param order_no 订单order_no
	 */
	@RequestMapping("updateContactsNoUser")
	@ResponseBody
	public String updateContactsNoUser(HttpServletRequest req, HttpServletResponse resp,String order_no,String userName, String userPhone,String userUserTime){
		try {
			int i = orderService.updateContactsMsg(order_no,userName,userPhone,userUserTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取max
	 * @param bean
	 * @param orderService
	 * @param max
	 * @return max = 0 未组团成功  max = 1 组团成功
	 */
	public static Integer getMax(Commodity bean,OrderService orderService,Integer max){
		try {
			int group = orderService.countByNUMBER(bean.getId());
			int group2 = orderService.countByNUMBER2(bean.getId());
			if(bean.getGroupNumber() != -1){
				if(group >= bean.getGroupNumber()){
					max = 1;
				}
			}
			if(bean.getGroupNumber2() != -1){
				if(group2 >= bean.getGroupNumber2()){
					max = 1;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return max;
	}

}
