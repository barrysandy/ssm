package com.xiaoshu.controller.wechat;

import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.xgb.springrabbitmq.dto.DtoMessage;
import com.xgb.springrabbitmq.publish.DeadLetterPublishService;
import com.xiaoshu.api.Set;
import com.xiaoshu.enumeration.EnumsMQName;
import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsASCIIChang;
import com.xiaoshu.tools.ToolsString;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.vo.Jssdk;
import com.xiaoshu.wechat.tools.JSSDKTiket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信JSSDK签名处理
 * @name: SignJSSDKController
 * @author: XGB
 * @date: 2018-03-2 11:21
 */
@Controller
@RequestMapping(value = "/interfaceJSSDK")

public class SignJSSDKController {

	private static Logger log = LoggerFactory.getLogger(SignJSSDKController.class);

	@Autowired private DeadLetterPublishService deadLetterPublishService;

	/**
	 * 页面签名JSSDK
	 * @param url 签名的url
	 * @param mapKey 签名的url 参数
	 * @param menuId 签名的公众号/菜单id
	 * @return String
	 * @author XGB
	 * @date 2018-02-28 10:45
	 */
	@RequestMapping("/sign")
	@ResponseBody
	public String sign(HttpServletRequest request,String url,String mapKey,String menuId){
		try{
			//准备签名参数
			//读取Map中的token
			MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
			Map<String, String> map = mapPublicNumber.getMap();
			if(map != null && map.size() == 0){
                System.out.println("------------ [out] map not data ------------");
				JobPublicAccount.ToRefreshMapJobPublicAccount();
			}
			String accessToken = map.get("accessToken" + menuId);
			String appId = map.get("appId" + menuId);
			//TODO 消息队列方式定时设置签名过期
			//TODO 死信延迟消息 QUEUE_NAME
			String urlMq =  Set.SYSTEM_URL + "interfaceMqJssdk/removeTicket";
			String paramsMq = "mapKey=" + ToolsASCIIChang.stringToAscii(mapKey);
			DtoMessage dtoMessage = new DtoMessage(UUID.randomUUID().toString(), urlMq, "get" ,paramsMq , null);
			String message = DtoMessage.transformationToJson(dtoMessage);
			deadLetterPublishService.send(EnumsMQName.DEAD_JSSDK_INVALID,message);

			Map<String,String> signMap = JSSDKTiket.reqTicketInterface(accessToken,appId,url,mapKey);
			String noncestr = signMap.get("noncestr");
			String signature = signMap.get("signature");
			String timestamp = signMap.get("timestamp");
			Jssdk jssdk = new Jssdk(appId, noncestr, signature, timestamp);
			String json = JSONUtils.toJSONString(jssdk);
			json = ToolsString.getStrRemoveBracket(json);
			log.info("------------ [log.info] sign json :" + json + " ------------");
			System.out.println("------------ [out] sign json :" + json + " ------------");
			return json;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
