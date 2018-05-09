package com.xiaoshu.controller.frontend.shop.order;

import com.xgb.springrabbitmq.publish.DeadLetterPublishService;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.controller.admin.order.AdminOrderRefundFunctionController;
import com.xiaoshu.entity.Order;
import com.xiaoshu.entity.OrderRefund;
import com.xiaoshu.enumeration.EnumsPayType;
import com.xiaoshu.service.OrderRefundService;
import com.xiaoshu.service.OrderService;
import com.xiaoshu.service.SellerService;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 商城模块
 * @name: ShopController
 * @author: XGB
 * @date: 2018-03-11 8:01
 */
@Controller
@RequestMapping(value = "/orderRefund")
public class OrderRefundController extends BaseController {

	@Resource private OrderService orderService;
	@Resource private OrderRefundService orderRefundService;
	@Resource private SellerService sellerService;

	@Autowired private DeadLetterPublishService deadLetterPublishService;

	/**
	 * 订单退款
	 * @param id 主键ID
	 * @return String
	 * @author XGB
	 * @date 2018-03-10 18:18
	 */
	@RequestMapping("/interfaceApplicationRefund")
	@ResponseBody
	public String interfaceApplicationRefund(int id){
		try{
			Order order = orderService.getById(id);
			if(order != null) {
				if(order.getTypeState() == 1 ) { //已付款
					int cout = orderRefundService.countByOrderNo(order.getOrderNo());
					if(cout == 0){
						String time = ToolsDate.getStringDate(ToolsDate.simpleSecond);
						if(order.getTransactionId() != null){
						    Double money = order.getOrderAmountMoney() * 100;
							int orderMoney = ( new Double(money) ).intValue();
                            int refundFee = orderMoney;
							String refundDesc = "";
							String refundAccount = "REFUND_SOURCE_RECHARGE_FUNDS";
							String orderRefundId = UUID.randomUUID().toString();
							OrderRefund orderRefund = new OrderRefund(orderRefundId, order.getTransactionId(), EnumsPayType.WECHAT_PAY, order.getOrderNo(), time, "", "", "",
									orderMoney, refundFee, "CNY", refundDesc,refundAccount, order.getOrderName(), 1, 1, order.getUserId(), order.getSellerId(),"");
							int i = orderRefundService.save(orderRefund);
							if(i > 0){
								//Todo 修改订单状态为4退款中  订单状态 0未付款 1已付款 2已消费 3退款中 4已退款 -1查询所有状态
								int resultOrder = orderService.updateOldTypeStateToOrderStateByOrderNo(orderRefund.getOrderNo(),3,1,"");
								if(resultOrder == 0) {
									orderRefundService.deleteById(orderRefundId);
									return JsonUtils.turnJson(false,"error",null);
								}
								return JsonUtils.turnJson(true,"success",null);
							}
						}else {
							return JsonUtils.turnJson(false,"error" + "No TransactionId ",null);
						}
					}
				}else if(order.getTypeState() == 5 ) { //发起重试
					OrderRefund orderRefund = orderRefundService.getByOrderNo(order.getOrderNo());
					if(orderRefund != null){
						if(orderRefund.getTypeState() == 2){
							AdminOrderRefundFunctionController.processPersistentMQ(orderRefundService, deadLetterPublishService , sellerService, orderRefund);
						}
					}
				}
			}
		}catch(Exception e){
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}
		return JsonUtils.turnJson(true,"success",null);
	}


}
