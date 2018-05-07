package com.xiaoshu.controller.frontend.shop.order;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoshu.entity.*;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.ToolsDate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;


@Component
@Controller
@RequestMapping("/orderWrite")
public class OrderWriteOffController {

	@Resource private OrderService orderService;
	@Resource private OrderCodesService orderCodesService;
	@Resource private OrderWriteOffByUserService orderWriteOffByUserService;
	@Resource private RecordWriteOffService recordWriteOffService;

	/**
	 * 核销核销码（Flower）
	 * URL ：orderWrite/WriteOffOrderCodeInUser?useCode=
	 * @param useCode 核销码useCode
	 * @return
	 */
	@RequestMapping("WriteOffOrderCodeInUser")
	public String WriteOffOrderCodeInUser(HttpServletRequest req,@RequestParam("useCode") String useCode,@RequestParam("orderId") Integer orderId,String menuId){
		String url = "mobile/user/userScanError";
		FocusedUserInfo user = (FocusedUserInfo) req.getSession().getAttribute("user");//核销者
		if(user != null){
			/**核销场景：
			 *  1.扫码者身份确认（是否为商家  1是向下执行 2否进入商城首页）
			 *  2.核销码所属商家确认（是否为商家的核销码  1是向下执行 2否 提示扫码商家端 不是自己店铺的订单）
			 *  3.核销码状态确认（1是向下执行 2否无效的使用码或者核销码已经使用）
			 *  4.开始核销
			 *    4.1 改变核销码为已经使用
			 *    4.2 判断该核销码的订单下的使用商品是否都核销，如果均已经核销则改变订单状态为已消费
			 *    4.3 设置核销码二维码图片准备清理状态
			 **/
			try {
				/** 找到核销码 */
				OrderCodes orderCode = orderCodesService.getOneByOrderId(orderId);
				if(orderCode != null){
					/** 找到核销码所属订单 */
					Order order = orderService.getById(orderCode.getOrderId());
					/** 判断判断是不是核销者 */
					int exitWriteOff = orderWriteOffByUserService.existService(order.getSellerId(), user.getId());
					if(exitWriteOff > 0){
						/** 可以进行核销 */
						req.setAttribute("order", order);
						req.setAttribute("orderId",orderId);
						req.setAttribute("useCode",useCode);
						url = "mobile/user/flower/userScanYes";
					}else{
						/** 非核销人员 */
						req.setAttribute("msg", "你不是核销人员!");
					}
				}else{
					/** 无效的核销码 */
					req.setAttribute("msg", "无效的核销码!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		req.setAttribute("menuId",menuId);
		return url;
	}
	

	
	/**
	 * 商家确认核销（Flower）
	 * URL ：orderWrite/ConfirmationFlowerInUserInUser?useCode=&orderId=&orderId=&userId=&menuId
	 * @param useCode 核销码useCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("ConfirmationFlowerInUserInUser")
	public String ConfirmationFlowerInUserInUser(HttpServletRequest req, HttpServletResponse resp,@RequestParam("useCode") String useCode,@RequestParam("orderId") Integer orderId,@RequestParam("userId") String userId,@RequestParam("menuId") String menuId){
		resp.setCharacterEncoding("UTF-8");
		FocusedUserInfo user = (FocusedUserInfo) req.getSession().getAttribute("user");//核销者
		try {
			OrderCodes orderCode = orderCodesService.getOneByOrderId(orderId);
			Order order = orderService.getById(orderCode.getOrderId());
			//4.开始核销
			//4.1 改变核销码为已经使用
			if(orderCode.getCodeState() == 1){
				String date = ToolsDate.getStringDate(ToolsDate.simpleSecond);
				int i = orderCodesService.updateCodeStateAndCreateTimeById(2, date, orderCode.getId());
				if(i > 0){
					String createTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
					RecordWriteOff recordWriteOff = new RecordWriteOff(UUID.randomUUID().toString(), useCode, order.getOrderNo(), order.getSellerId(), user.getId(), order.getUserId(),createTime);
					int hasSaveRecordWrite = recordWriteOffService.saveRecordWriteOffService(recordWriteOff);
					if(hasSaveRecordWrite > 0){
						int j = orderService.updateOldTypeStateToOrderStateByOrderNo(order.getOrderNo(), 2,1,"");
						resp.getWriter().print(j);
					}else{
						resp.getWriter().print(0);
						recordWriteOffService.deleteById(recordWriteOff.getId());
						orderCodesService.updateCodeStateAndCreateTimeById(1, date, orderCode.getId());
					}
				}
				else{
					//核销失败
					resp.getWriter().print(0);
					orderCodesService.updateCodeStateAndCreateTimeById(1, date, orderCode.getId());
				}
			}else{
				resp.getWriter().print(-1);//已经核销了
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
