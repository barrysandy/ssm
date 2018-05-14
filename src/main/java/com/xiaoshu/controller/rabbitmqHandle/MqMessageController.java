package com.xiaoshu.controller.rabbitmqHandle;

import com.xiaoshu.controller.BaseController;
import com.xiaoshu.entity.*;
import com.xiaoshu.enumeration.EnumsMQAck;
import com.xiaoshu.service.*;
import com.xiaoshu.tools.ToolsDate;
import com.xiaoshu.tools.ToolsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * 消息队列方式处理会议业务
 * @name: MqOrderController
 * @author: XGB
 * @date: 2018-05-14 16:56
 */
@Controller
@RequestMapping(value = "/interfaceMqMeeting")
public class MqMessageController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(MqMessageController.class);

	@Resource private MeetingService meetinmgService;
	@Resource private MeetingSignService meetingSignService;

	/**
	 * (所有消息处理的返回如果为‘ok’则ack消息，‘fail’则nack消息，只对需要手动确认的消息有效)
	 * RabbitMQ 异步生成会议条形码Controller
	 * URL : interfaceMqMeeting/createBarCode
	 * @param meetingId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createBarCode", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public String createOrder(HttpServletResponse resp,HttpServletRequest request,String meetingId){
		Integer createTotal = 0;
		try {
			if(meetingId != null) {
				int total = meetingSignService.getCountByMeetingId(meetingId);
				int pageSize = 30;
				int totalPage = ToolsPage.totalPage(total, pageSize);//总页数
				if (totalPage > 0) {
					for (int i = 0; i < totalPage; i++) {
						int index = i * pageSize;
						List<MeetingSign> list = meetingSignService.getListByMeetingId(index, pageSize, meetingId);
						createTotal = createTotal + list.size();
						Iterator<MeetingSign> iterator = list.iterator();
						while (iterator.hasNext()) {
							MeetingSign meetingSign = iterator.next();
							if(meetingSign != null){
								String signCode = meetingSign.getSignCode();
								if(signCode != null && meetingSign.getPhone() != null){
									// TODO 生成条形码

								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return EnumsMQAck.ACK_FAIL;
		}
		return EnumsMQAck.ACK_OK;
	}

}
