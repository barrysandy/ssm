package com.xiaoshu.controller.wechat;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.wechat.messagehandle.MessageService;
import com.xiaoshu.wechat.pojo.WeChat;
import com.xiaoshu.wechat.tools.SignUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/wechat")

/**
 * 微信消息处理
 * @name: WeChatController
 * @author: XGB
 * @date: 2018-01-17 10:30
 */
public class WeChatController {

	private static Logger log = LoggerFactory.getLogger(WeChatController.class);

	/**
	 * 微信配置签名
	 * @param wc 微信验证
	 * @param sign 由sign获取公众号信息sign也就是menuId
	 * @return echostr 签名后的字符串 OR null
	 * @author XGB
	 * @date 2018-01-17 10:32
	 */
	@RequestMapping(value="/interfaceApi",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String wxtInterface(WeChat wc,String sign){
		try{
			if(wc!=null){
				String signature = wc.getSignature(); // 微信加密签名
				String timestamp = wc.getTimestamp(); // 时间戳
				String nonce = wc.getNonce();// 随机数
				String echostr = wc.getEchostr();// 随机字符串

				//读取Map中的token
				MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
				Map<String, String> map = mapPublicNumber.getMap();
				if(map != null && map.size() == 0){
                    System.out.println("map not data");
					JobPublicAccount.ToRefreshMapJobPublicAccount();
				}
				String token = map.get("token" + sign);
				if(token != null){
					// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
					if (SignUtil.checkSignature(signature, timestamp, nonce,token)) {
						return echostr;
					} else {
						System.out.println("Not a request from WeChat server, please be careful!");
					}
				}else{
					log.info("When WeChat configuring its signature, token gets failed, and there is no token in map or sign= "+sign+" The public number has not been opened!!");
				}

			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 微信消息处理
	 * @return 处理后的回复消息
	 * @author XGB
	 * @date 2018-01-17 10:34
	 */
	@RequestMapping(value="/interfaceApi",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getWeiXinMessage(HttpServletRequest request, HttpServletResponse response,String sign){
		try {
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			request.setCharacterEncoding("UTF-8");
			//微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
			response.setCharacterEncoding("UTF-8"); //在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
//			log.info("===微信消息处理_当前公众号标识：" + sign);
			//读取Map中的token
			MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
			Map<String, String> map = mapPublicNumber.getMap();
			if(map.isEmpty()){
				System.out.println("map not data");
				JobPublicAccount.ToRefreshMapJobPublicAccount();
			}
            //调用CoreService类的processRequest方法接收、处理消息，并得到处理结果；
			String resp = MessageService.processRequest(request,sign);
			return resp;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


//	/**
//	 * 移除JSSDK的签名 wechat/interfaceRemoveJSSDKTicket?mapKey=
//	 * @param mapKey 移除的key值
//	 * @return 处理后的回复消息
//	 * @author XGB
//	 * @date 2018-01-30 17:26
//	 */
//	@ResponseBody
//	@RequestMapping(value="/interfaceRemoveJSSDKTicket",method = RequestMethod.POST)
//	public String interfaceRemoveJSSDKTicket(HttpServletRequest req,HttpServletResponse resp,@RequestParam("mapKey") String mapKey){
//		try {
//			Map<String,TicketObject> map = MapTicket.getInstance().getMap();
//			if(map.get(mapKey) != null){
//				map.remove(mapKey);
//				resp.getWriter().print(1);
//			}else{
//				resp.getWriter().print(0);
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
