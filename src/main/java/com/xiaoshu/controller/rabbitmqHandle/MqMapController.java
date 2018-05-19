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

	private static int total = 0;

	/**
	 * (所有消息处理的返回如果为‘ok’则ack消息，‘fail’则nack消息，只对需要手动确认的消息有效)
	 * RabbitMQ 设置mapKey的值失效Controller
	 * URL : interfaceMqMap/removeUserClickGroupMap
	 * @param mapKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/removeUserClickGroupMap")
	public String removeUserClickGroupMap(@RequestParam("mapKey") String mapKey){
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
			total ++;
			if(total >= 5){
				System.out.println("com.xiaoshu.controller.rabbitmqHandle.MqMapController.removeUserClickGroupMap 异常超过5次 直接ACK_OK 将异常记录在MQ异常表 !!!  " + e);
				return EnumsMQAck.ACK_OK;
			}else {
				return EnumsMQAck.ACK_FAIL;
			}

		}
		return EnumsMQAck.ACK_OK;
	}

}
