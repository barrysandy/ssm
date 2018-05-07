package com.xiaoshu.controller.rabbitmqHandle;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.enumeration.EnumsMQAck;
import com.xiaoshu.po.UserClickGroup;
import com.xiaoshu.po.UserClickGroupMap;
import com.xiaoshu.tools.ToolsASCIIChang;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 消息队列方式处理Map
 * @name: MqMapController
 * @author: XGB
 * @date: 2018-05-3 15:32
 */
@Controller
@RequestMapping(value = "/interfaceMqMap")
public class MqMapController extends BaseController {

	/**
	 * (所有消息处理的返回如果为‘ok’则ack消息，‘fail’则nack消息，只对需要手动确认的消息有效)
	 * RabbitMQ 设置mapKey的值失效Controller
	 * URL : interfaceMqMap/removeUserClickGroupMap
	 * @param mapKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/removeUserClickGroupMap", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public String removeTicket(@RequestParam("mapKey") String mapKey){
		if(mapKey != null){
			if(!"".equals(mapKey)){
				mapKey = ToolsASCIIChang.asciiToString(mapKey);
			}
		}
		try {
			Map<String, UserClickGroup> map = UserClickGroupMap.getInstance().getMap();
			map.remove(mapKey);
			System.out.println("------------ [Queue Message] Remove expired map mapKey : "  + mapKey + " ------------");
		} catch (Exception e) {
			e.printStackTrace();
			return EnumsMQAck.ACK_FAIL;
		}
		return EnumsMQAck.ACK_OK;
	}

}
