package com.xiaoshu.controller.rabbitmqHandle;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.Commodity;
import com.xiaoshu.entity.Order;
import com.xiaoshu.entity.OrderCodes;
import com.xiaoshu.enumeration.EnumsMQAck;
import com.xiaoshu.service.CommodityService;
import com.xiaoshu.service.OrderCodesService;
import com.xiaoshu.service.OrderService;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.wechat.pojo.TicketObject;
import com.xiaoshu.wechat.tools.MapTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

/**
 * 消息队列方式处理JSSDK
 * @name: MqJSSDKController
 * @author: XGB
 * @date: 2018-05-3 15:32
 */
@Controller
@RequestMapping(value = "/interfaceMqJssdk")
public class MqJSSDKController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(MqJSSDKController.class);

	/**
	 * (所有消息处理的返回如果为‘ok’则ack消息，‘fail’则nack消息，只对需要手动确认的消息有效)
	 * RabbitMQ 设置jssdk签名失效Controller
	 * URL : interfaceMqJssdk/removeTicket
	 * @param mapKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/removeTicket", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public String removeTicket(@RequestParam("mapKey") String mapKey){
		if(mapKey != null){
			if(!"".equals(mapKey)){
				mapKey = ToolsASCIIChang.asciiToString(mapKey);
			}
		}
		try {
			Map<String,TicketObject> map = MapTicket.getInstance().getMap();
			if(map.get(mapKey) != null){
				map.remove(mapKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return EnumsMQAck.ACK_FAIL;
		}
		return EnumsMQAck.ACK_OK;
	}

}
