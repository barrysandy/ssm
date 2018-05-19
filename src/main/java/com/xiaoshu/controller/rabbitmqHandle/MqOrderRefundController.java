package com.xiaoshu.controller.rabbitmqHandle;

import com.xgb.springrabbitmq.dto.DtoMessage;
import com.xgb.springrabbitmq.publish.DeadLetterPublishService;
import com.xiaoshu.api.Set;
import com.xiaoshu.controller.BaseController;
import com.xiaoshu.controller.wechat.WeChatPayController;
import com.xiaoshu.entity.*;
import com.xiaoshu.enumeration.EnumsMQAck;
import com.xiaoshu.enumeration.EnumsMQName;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.sendMsg.EnumsTemplateType;
import com.xiaoshu.vo.WeChatPayRefund;
import com.xiaoshu.wechat.payrefund.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * 消息队列方式处理订单退款业务
 * @name: MqOrderController
 * @author: XGB
 * @date: 2018-05-7 15:18
 */
@Controller
@RequestMapping(value = "/interfaceMqOrderRefund")
public class MqOrderRefundController extends BaseController {

	@Resource private OrderRefundService orderRefundService;
	@Resource private OrderService orderService;
	@Resource private WaterBillService waterBillService;
	@Resource private MessageRecordService messageRecordService;

	@Autowired private DeadLetterPublishService deadLetterPublishService;

	private static int total = 0;

	/**
	 * (所有消息处理的返回如果为‘ok’则ack消息，‘fail’则nack消息，只对需要手动确认的消息有效)
	 * RabbitMQ 更新OrderRefund退款结果描述Controller
	 * URL : interfaceMqOrderRefund/updateResult
	 * @param orderNo
	 * @param refundResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateResult")
	public String updateResult(String orderNo,String refundResult,String sign){
		if(sign != null){ if(!"".equals(sign)){ sign = ToolsASCIIChang.asciiToString(sign); } }//解析sign
		if(refundResult != null){ if(!"".equals(refundResult)){ refundResult = ToolsASCIIChang.asciiToString(refundResult); } }//解析refundResult
		try {
			if(orderNo != null && refundResult != null){
				OrderRefund orderRefund = orderRefundService.getByOrderNo(orderNo);
				Order order = orderService.getByOrderNo(orderRefund.getOrderNo());
				WeChatPayRefund weChatPayRefund = WeChatPayController.getWeChatPayRefundBean(refundResult);
				String descM = "";
				if(weChatPayRefund != null){
					// TODO 退款失败
					if("FAIL".equals(weChatPayRefund.getResult_code())){
						//TODO 发送失败短信
						messageRecordService.sendRefundFailMsg(order.getId(), EnumsTemplateType.REFUND_FAIL,sign,weChatPayRefund.getErr_code_des());
						//TODO 更新订单退款失败状态
						descM = "退款状态:" + weChatPayRefund.getResult_code() + " 描述:" + weChatPayRefund.getErr_code_des();
						orderService.updateOldTypeStateToOrderStateByOrderNo(orderRefund.getOrderNo(),5,3,descM);
					}
					if("SUCCESS".equals(weChatPayRefund.getResult_code())){
						if("OK".equals(weChatPayRefund.getReturn_msg())){//表示退款成功，修改退款状态以及订单状态以及流水
							//Todo 发送退款短信
							messageRecordService.sendRefundMsg(order.getId(), EnumsTemplateType.REFUND);
							//Todo 修改退款状态以及订单状态以及流水
							String time = ToolsDate.getStringDateToFormat(new Date(), ToolsDate.simpleSecond);
							orderRefund.setReturnTime(time);
							orderRefund.setTypeState(2);
							orderRefund.setDescM(JSONUtils.toJSONString(weChatPayRefund));
							int result = orderRefundService.updateAll(orderRefund);
							if(result > 0) {//Todo 订单状态 0未付款 1已付款 2已消费 3退款中 4已退款 5退款失败 6免费可以使用的订单 -1查询所有状态
								descM = "退款状态:" + weChatPayRefund.getResult_code() + " 描述:" + weChatPayRefund.getReturn_msg();
								orderService.updateOldTypeStateToOrderStateByOrderNo(orderRefund.getOrderNo(),4,3,descM);
								//Todo 修改流水信息
								WaterBill waterBill = waterBillService.getByOrderNo(orderRefund.getOrderNo());
								if(waterBill != null){
									waterBill.setStatus(3);
									waterBillService.updateStateById(waterBill.getId(),3);
								}
							}
						}
					}

				}
				int refund = orderRefundService.updateRefundResultByOderNo(descM,orderNo);
				System.out.println("RabbitMQ 更新OrderRefund退款结果描述 descM " + descM + " OderNo " + orderNo );
				if(refund > 0){
					return EnumsMQAck.ACK_OK;
				}else {
					return EnumsMQAck.ACK_FAIL;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			total ++;
			if(total >= 5){
				System.out.println("com.xiaoshu.controller.rabbitmqHandle.MqOrderRefundController.updateResult 异常超过5次 直接ACK_OK 将异常记录在MQ异常表 !!!  " + e);
				return EnumsMQAck.ACK_OK;
			}else {
				return EnumsMQAck.ACK_FAIL;
			}

		}
		return EnumsMQAck.ACK_FAIL;
	}


	/**
	 * (所有消息处理的返回如果为‘ok’则ack消息，‘fail’则nack消息，只对需要手动确认的消息有效)
	 * RabbitMQ 微信退款申请处理Controller
	 * @param orderNo 退款申请 orderNo
	 * @return String
	 * @author XGB
	 * @date 2018-03-10 18:18
	 */
	@RequestMapping("/interfaceWechatReurn")
	@ResponseBody
	public String interfaceWechatReurn(HttpServletRequest request,String orderNo,String menuId,String sign){
		try{
			OrderRefund orderRefund = orderRefundService.getByOrderNo(orderNo);
			if(orderRefund != null) {
				if(orderRefund.getTypeState() == 2 ) { //已提交/待处理
					//Todo 访问微信退款接口
					String jsonStr = Refund.doWechatRefund(request,orderRefund,menuId);
					System.out.println("------------ [Order Refund XML] Refund doWechatRefund: jsonStr: " + jsonStr +" ------------");
					if( jsonStr != null){
						if(!"".equals(jsonStr)) {
							//TODO Rabbit消息队列 更新退款结果
							String url = Set.SYSTEM_URL + "interfaceMqOrderRefund/updateResult";
							String params = "orderNo=" + orderRefund.getOrderNo() +"&refundResult=" + ToolsASCIIChang.stringToAscii(jsonStr) + "&sign=" + sign;
							DtoMessage dtoMessage = new DtoMessage(UUID.randomUUID().toString(), url, "post" ,params , null);
							String message = DtoMessage.transformationToJson(dtoMessage);
							deadLetterPublishService.send(EnumsMQName.DEAD_TEN_SECONDS , message);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			total ++;
			if(total >= 5){
				System.out.println("com.xiaoshu.controller.rabbitmqHandle.MqOrderRefundController.interfaceWechatReurn 异常超过5次 直接ACK_OK 将异常记录在MQ异常表 !!!  " + e);
				return EnumsMQAck.ACK_OK;
			}else {
				return EnumsMQAck.ACK_FAIL;
			}

		}
		return EnumsMQAck.ACK_OK;
	}

}
