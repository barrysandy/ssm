package com.xiaoshu.wechat.messagehandle;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.xiaoshu.api.Api;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.wechat.robot.MessageResponse;
import com.xiaoshu.wechat.tools.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 微信消息服务类
 * @name: MessageService
 * @author: XGB
 * @date: 2018-01-17 10:30
 */
public class MessageService {
	
	static Logger logger = LoggerFactory.getLogger(MessageService.class);
	
	/**
	 * 处理微信发来的请求
	 * @param request
	 * @return
	 */
	
	public static String  processRequest(HttpServletRequest request,String menuId) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";
			// xml请求解析
			// 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 从HashMap中取出消息中的字段；
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			//System.out.println("公众帐号:"+toUserName);
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 消息内容
			String content = requestMap.get("Content");
			// 从HashMap中取出消息中的字段；
			// 消息id，64位整型
			//String MsgId = requestMap.get("MsgId");
			//System.out.println("消息id，64位整型："+MsgId);
			//logger.info("fromUserName is:" +fromUserName+" toUserName is:" +toUserName+" msgType is:" +msgType);
			

			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {// 文本消息（完成）
				return MessageServiceUtils.text(menuId,request, requestMap, fromUserName, toUserName, msgType, content, respMessage, respContent);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
				String eventType = requestMap.get("Event");// 事件类型
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 订阅（完成）
					return MessageServiceUtils.subScribe(menuId,request, requestMap, fromUserName, toUserName, msgType, content, respMessage, respContent);
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅（完成）
					//  取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
					//  需要更新用户的关注状态
					String url = Api.UPDATEUSERSUBSCRIBE;
					String param = "menuId=" + menuId + "&openid=" + fromUserName +"&subscribe=0";
					ToolsHttpRequest.sendGet(url, param);
					return "QX";

				} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
					return MessageServiceUtils.click(menuId,request, requestMap, fromUserName, toUserName, msgType, content, respMessage, respContent);
				}
			}
			//开启微信声音识别测试
			else if(msgType.equals("voice"))
			{
				String recvMessage = requestMap.get("Recognition");
				//respContent = "收到的语音解析结果：" + recvMessage;
				if(recvMessage!=null){
					return  MessageServiceUtils.text(menuId,request, requestMap, fromUserName, toUserName, msgType, recvMessage, respMessage, respContent);
				}else{
					respContent = "您说的太模糊了，能不能重新说下呢？";
					return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
				}
			}
			//拍照功能
			else if(msgType.equals("pic_sysphoto"))
			{
			}
			else
			{
				return MessageResponse.getTextMessage(fromUserName , toUserName , "您说的太模糊了，能不能重新说下呢？"); 
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

	
}
