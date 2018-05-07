package com.xiaoshu.controller.frontend.shop.order;

import com.xiaoshu.api.Api;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.tools.ToolsString;
import com.xiaoshu.tools.ssmImage.ToolsImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * 商城模块--组团商品下单
 * @name: ShopController
 * @author: XGB
 * @date: 2018-03-11 8:01
 */
@Controller
@RequestMapping(value = "/order")
public class OrderGroupController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(OrderGroupController.class);

	@Resource private OrderService orderService;
	@Resource private OrderCodesService orderCodesService;
	@Resource private CommodityService commodityService;
	@Resource private CommodityGroupService commodityGroupService;
	@Resource private CommodityGroupMemberService commodityGroupMemberService;
	@Resource private CommodityPriceListService commodityPriceListService;

	/**
	 *Controller_Function ：组团商品下单--建立团
	 *Access URL ：order/placeAnOrderGroupInUser?id=&Price=&Number=&Total=
	 * @param id 商品id
	 * @param total 总金额
	 * @return  (1) 1下单成功  (2)-1参数错误 (3)-2商品错误 (4)-3生成订单编失败 (5)-4商品库存不足 (6)0订单保存失败
	 */
	@RequestMapping("placeAnOrderGroupInUser")
	@ResponseBody
	public String placeAnOrderGroupInUser(HttpServletRequest req, HttpServletResponse resp,Integer id,Double total,String userPhone,String userUserTime,String userName,String type){
		FocusedUserInfo user = (FocusedUserInfo) req.getSession().getAttribute("user");
		/**下单场景：
		 *  1.验证参数
		 **/
		try {
			if(id != null && !"".equals(id) && total != null && !"".equals(total) && userPhone != null && !"".equals(userPhone) && userUserTime != null && !"".equals(userUserTime)){
				Commodity commodity = commodityService.findCommodityByIdService(id);
				if(commodity != null){
					String img = ToolsImage.getFristImageUrlByServer(commodity.getArrayImg());//封面图片
					String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
					String user_id = null;
					if(user != null){ user_id = user.getId(); }
					String orderName = commodity.getCommodityName();
					String numberDescM = " 组团票 * 1 ";

					/** 活动订单的消费码 */
					String useCodeOder = orderService.getOrderNumber();
					int exitOder = orderService.countByUseCode(useCodeOder);
					while (exitOder > 0) {
						/** 重新获取Order useCode */
						useCodeOder = orderService.getOrderCode();
						exitOder = orderService.countByUseCode(useCodeOder);
					}

					//TODO 判断需要创建团还是已经存在未付款的团
					String groupId = null ;
					CommodityGroupMember commodityGroupMember = commodityGroupMemberService.getByUserIdAndCId(id,user.getId());
					if(type != null && "create".equals(type)){
						if(commodityGroupMember != null){
							groupId = commodityGroupMember.getGroupId();
							log.info("------------ [LOG["+nowTime+"] Group Exit!!! groupId is :" + groupId +"------------");
						}else{
							String groupTime = null;
							CommodityPriceList commodityPriceList = commodityPriceListService.getByCommodityIdAndDesC(id,userUserTime);
							if(commodityPriceList != null) {
								groupTime = commodityPriceList.getPriceTime() + " 00:00:00";
							}
							//不存在组团 创建新组团
							groupId = UUID.randomUUID().toString();
							String groupCode = ToolsDate.getStringDate(ToolsDate.dtLongSecond) + ToolsString.generateRandNumber(4);
							CommodityGroup commodityGroup = new CommodityGroup(groupId,groupCode, commodity.getId(), new Date(),groupTime, "", 0 , commodity.getCreateGroupNumber(), 0);
							commodityGroupService.save(commodityGroup);
							System.out.println("------------ [LOG["+nowTime+"] ------------");
							log.info("------------ [LOG["+nowTime+"] Group Create!!! groupId is :" + groupId +"------------");
							System.out.println("------------ [LOG["+nowTime+"] ------------");
						}

					}

					Order order = orderService.getByGroupIdAndUserId(groupId,user.getId());
					if(order != null){
						//TODO 加入组团成员
						if(commodityGroupMember != null){
							System.out.println("------------ [LOG["+nowTime+"] Exit GroupMember!!!------------");
						}else {
							commodityGroupMember = new CommodityGroupMember(UUID.randomUUID().toString(), commodity.getId(), new Date(), "", user.getId(), 1, order.getOrderNo(), groupId, 0);
							commodityGroupMemberService.save(commodityGroupMember);
							System.out.println("------------ [LOG["+nowTime+"] Save GroupMember!!!------------");
						}
                        resp.getWriter().print(order.getId());//下单成功
					}else {
						//TODO 创建订单
						order = new Order(0, orderName, "",nowTime, null, "",  numberDescM, total, 0, 0, user_id, userName, userPhone, userUserTime, commodity.getSellerId(), nowTime, img, useCodeOder,"",null,0,0,commodity.getId(),2,groupId);
						String order_no = orderService.getOrderNumber();;//生产订单编号
						if(order_no != null){
							if(!"".equals(order_no)){
								order.setOrderNo(order_no);//订单
								int i = orderService.save(order);//保存订单
								if(i > 0){
									log.info("------------ [System Message] : New Order_NO = " + order.getOrderNo() + " time ：" + nowTime + " ------------");
									String useCode = orderService.getOrderCode();
									int exit = orderCodesService.countByUseCode(useCode);
									while (exit > 0) {
										/** 重新获取useCode */
										useCode = orderService.getOrderCode();
										exit = orderCodesService.countByUseCode(useCode);
									}
									OrderCodes ordecode = new OrderCodes(UUID.randomUUID().toString(), order.getId(), order.getOrderNo(), "", useCode, "", nowTime, user.getId(), commodity.getId(), 0, 1);
									int saveState = orderCodesService.save(ordecode);
									if(saveState > 0){
										log.info("------------ [System Message] : New Order_CODE = " + useCode + " time ：" + nowTime + " ------------");
									}
									//TODO 加入组团成员
									if(commodityGroupMember != null){
										System.out.println("------------ [LOG["+nowTime+"] Exit GroupMember!!!------------");
									}else {
										commodityGroupMember = new CommodityGroupMember(UUID.randomUUID().toString(), commodity.getId(), new Date(), "", user.getId(), 1, order_no, groupId, 0);
										commodityGroupMemberService.save(commodityGroupMember);
										System.out.println("------------ [LOG["+nowTime+"] Save GroupMember!!!------------");
									}
									resp.getWriter().print(order.getId());//下单成功
								}else{
									resp.getWriter().print(0);//0订单保存失败
								}
							}else{
								resp.getWriter().print(-3);//-3生成订单编失败
							}
						}
					}
				}else{
					resp.getWriter().print(-2);//-2商品错误
				}
			}else{
				resp.getWriter().print(-1);//-1参数错误
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 *Controller_Function ：组团商品下单--参加团
	 *Access URL ：order/placeAnOrderGroupJoinInUser?id=&Price=&Number=&Total=
	 * @param id 商品id
	 * @param total 总金额
	 * @return  (1) 1下单成功  (2)-1参数错误 (3)-2商品错误 (4)-3生成订单编失败 (5)-4商品库存不足 (6)0订单保存失败
	 */
	@RequestMapping("placeAnOrderGroupJoinInUser")
	@ResponseBody
	public String placeAnOrderGroupJoinInUser(HttpServletRequest req, HttpServletResponse resp,Integer id,Double total,String userPhone,String userUserTime,String userName,String type,String groupId){
		FocusedUserInfo user = (FocusedUserInfo) req.getSession().getAttribute("user");
		/**下单场景：
		 *  1.验证参数
		 **/
		try {
			if(id != null && !"".equals(id) && total != null && !"".equals(total) && userPhone != null && !"".equals(userPhone) && userUserTime != null && !"".equals(userUserTime) && groupId != null && !"".equals(groupId)){
				Commodity commodity = commodityService.findCommodityByIdService(id);
				if(commodity != null){
					String img = ToolsImage.getFristImageUrlByServer(commodity.getArrayImg());//封面图片
					String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
					String user_id = null;
					if(user != null){ user_id = user.getId(); }
					String orderName = commodity.getCommodityName();
					String numberDescM = " 组团票 * 1 ";

					/** 活动订单的消费码 */
					String useCodeOder = orderService.getOrderCode();
					int exitOder = orderService.countByUseCode(useCodeOder);
					while (exitOder > 0) {
						/** 重新获取Order useCode */
						useCodeOder = orderService.getOrderCode();
						exitOder = orderService.countByUseCode(useCodeOder);
					}

					//TODO 判断团是否存在，存在继续 不存在提示下单失败，团不存在
					CommodityGroup commodityGroup = null;
					if(type != null && "join".equals(type)){
						//查询当前的团
						commodityGroup = commodityGroupService.getById(groupId);
					}
					if(commodityGroup == null){
						resp.getWriter().print(-4);//团不存在
					}else {
						//TODO 判断当前团的状态
						if(commodityGroup.getStatus() == 1){
							resp.getWriter().print(-5);//团已经满员
						}else {
							CommodityGroupMember commodityGroupMember = commodityGroupMemberService.getByUserIdAndCId(id,user.getId());
							//TODO 创建订单
							Order order = orderService.getByGroupIdAndUserId(groupId,user.getId());
							if(order != null){
								//TODO 加入组团成员
								if(commodityGroupMember != null){
									System.out.println("------------ [LOG["+nowTime+"] Exit GroupMember!!!------------");
								}else {
									commodityGroupMember = new CommodityGroupMember(UUID.randomUUID().toString(), commodity.getId(), new Date(), "", user.getId(), 0, order.getOrderNo(), groupId, 0);
									commodityGroupMemberService.save(commodityGroupMember);
									System.out.println("------------ [LOG["+nowTime+"] Save GroupMember!!!------------");
								}
								resp.getWriter().print(order.getId());//下单成功
							} else {
								order = new Order(0, orderName, "",nowTime, null, "",  numberDescM, total, 0, 0, user_id, userName, userPhone, userUserTime, commodity.getSellerId(), nowTime, img, useCodeOder,"",null,0,0,commodity.getId(),2,groupId);
								String order_no = orderService.getOrderNumber();//生产订单编号
								if(!"".equals(order_no)){
									order.setOrderNo(order_no);//订单
									int i = orderService.save(order);//保存订单
									if(i > 0){
										log.info("------------ [System Message] : New Order_NO = " + order.getOrderNo() + " time ：" + nowTime + " ------------");
										String useCode = orderService.getOrderCode();;
										int exit = orderCodesService.countByUseCode(useCode);
										while (exit > 0) {
											/** 重新获取useCode */
											useCode = orderService.getOrderCode();
											exit = orderCodesService.countByUseCode(useCode);
										}
										OrderCodes ordecode = new OrderCodes(UUID.randomUUID().toString(), order.getId(), order.getOrderNo(), "", useCode, "", nowTime, user.getId(), commodity.getId(), 0, 1);
										int saveState = orderCodesService.save(ordecode);
										if(saveState > 0){
											log.info("------------ [System Message] : New Order_CODE = " + useCode + " time ：" + nowTime + " ------------");
										}
										//TODO 加入组团成员
										if(commodityGroupMember != null){
											System.out.println("------------ [LOG["+nowTime+"] Exit GroupMember!!!------------");
										}else {
											commodityGroupMember = new CommodityGroupMember(UUID.randomUUID().toString(), commodity.getId(), new Date(), "", user.getId(), 0, order_no, groupId, 0);
											commodityGroupMemberService.save(commodityGroupMember);
											System.out.println("------------ [LOG["+nowTime+"] Save GroupMember!!!------------");
										}
										resp.getWriter().print(order.getId());//下单成功
									}else{
										resp.getWriter().print(0);//0订单保存失败
									}
								}else{
									resp.getWriter().print(-3);//-3生成订单编失败
								}
							}

						}
					}


				}else{
					resp.getWriter().print(-2);//-2商品错误
				}
			}else{
				resp.getWriter().print(-1);//-1参数错误
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
