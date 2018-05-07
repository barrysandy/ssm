package com.xiaoshu.controller.rabbitmqHandle;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.Commodity;
import com.xiaoshu.entity.Order;
import com.xiaoshu.entity.OrderCodes;
import com.xiaoshu.enumeration.EnumsMQAck;
import com.xiaoshu.service.CommodityService;
import com.xiaoshu.service.OrderCodesService;
import com.xiaoshu.service.OrderService;
import com.xiaoshu.tools.ToolsDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 消息队列方式处理订单业务
 * @name: MqOrderController
 * @author: XGB
 * @date: 2018-05-3 15:32
 */
@Controller
@RequestMapping(value = "/interfaceMqOrder")
public class MqOrderController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(MqOrderController.class);

	@Resource private OrderService orderService;
	@Resource private OrderCodesService orderCodesService;
	@Resource private CommodityService commodityService;

	/**
	 * (所有消息处理的返回如果为‘ok’则ack消息，‘fail’则nack消息，只对需要手动确认的消息有效)
	 * RabbitMQ 创建抽奖中奖订单Controller
	 * URL : interfaceMqOrder/createOrder
	 * @param userId
	 * @param userName
	 * @param userPhone
	 * @param commodityId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createOrder", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public String createOrder(HttpServletResponse resp,HttpServletRequest request,String userId,String userName,String userPhone,int commodityId){
		try {
			Commodity commodity = commodityService.findCommodityByIdService(commodityId);
			String img = commodity.getImage();//封面图片
			String time = ToolsDate.getStringDate(ToolsDate.simpleSecond);
			Order order = new Order(0, commodity.getCommodityName(), "",time, null, "",  "", 0.00,
					1, 0, userId, userName, userPhone, "", commodity.getSellerId(), time, img, null,null,null,
					0,0,commodity.getId(),0,"");
			String order_no = orderService.getOrderNumber();//生产订单编号 synchronize方法
			if(!"".equals(order_no)){
				order.setOrderNo(order_no);//订单
				int i = orderService.save(order);//保存订单
				if(i > 0){
					String useCode = orderService.getOrderCode();
					int exit = orderCodesService.countByUseCode(useCode);
					while (exit > 0) {
						/** 重新获取useCode */
						useCode = orderService.getOrderCode();
						exit = orderCodesService.countByUseCode(useCode);
					}
					OrderCodes ordecode = new OrderCodes(UUID.randomUUID().toString(), order.getId(), order.getOrderNo(), "", useCode, "", time, userId, commodity.getId(), 0, 1);
					int saveState = orderCodesService.save(ordecode);
					if(saveState > 0){
						log.info("------------ [System Message] : New Order_CODE = " + useCode + " time ：" + time + " ------------");
					}
					return EnumsMQAck.ACK_OK;
				}else{
					return EnumsMQAck.ACK_FAIL;
				}
			}else{
				return EnumsMQAck.ACK_FAIL;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return EnumsMQAck.ACK_FAIL;
		}
	}

}
