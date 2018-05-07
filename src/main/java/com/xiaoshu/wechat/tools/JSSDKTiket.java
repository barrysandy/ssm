package com.xiaoshu.wechat.tools;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.DelayQueue;

import javax.servlet.http.HttpServletRequest;

import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.wechat.api.WeChatAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.wechat.pojo.TicketObject;
import net.sf.json.JSONObject;

public class JSSDKTiket {

	private static Logger log = LoggerFactory.getLogger(JSSDKTiket.class);
	
	//获取jsapi_ticket
	public static String getTicket(String access_token) {  
	    String ticket = null;  
	    String url = WeChatAPI.GetWebTicket.replaceAll("ACCESS_TOKEN",access_token);//这个url链接和参数不能变
	    try {  
	        URL urlGet = new URL(url);  
	        HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();  
	        http.setRequestMethod("GET"); // 必须是get方式请求  
	        http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
	        http.setDoOutput(true);  
	        http.setDoInput(true);  
	        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒  
	        System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒  
	        http.connect();  
	        InputStream is = http.getInputStream();  
	        int size = is.available();  
	        byte[] jsonBytes = new byte[size];  
	        is.read(jsonBytes);  
	        String message = new String(jsonBytes, "UTF-8");  
	        JSONObject demoJson = JSONObject.fromObject(message);  
//	        System.out.println("JSON字符串："+demoJson);  
	        ticket = demoJson.getString("ticket");  
	        is.close();  
	    } catch (Exception e) {  
	            e.printStackTrace();  
	    }  
	    return ticket;  
	}
	
	//拿到了jsapi_ticket之后就要参数名排序和拼接字符串，并加密了。以下为sha1的加密算法：
	public static String SHA1(String decript) {  
	    try {  
	        MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");  
	        digest.update(decript.getBytes());  
	        byte messageDigest[] = digest.digest();  
	        // Create Hex String  
	        StringBuffer hexString = new StringBuffer();  
	        // 字节数组转换为 十六进制 数  
	            for (int i = 0; i < messageDigest.length; i++) {  
	                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);  
	                if (shaHex.length() < 2) {  
	                    hexString.append(0);  
	                }  
	                hexString.append(shaHex);  
	            }  
	            return hexString.toString();  
	   
	        } catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();  
	        }  
	        return "";  
	}
	
	
	
	/**
	 * 获取页面签名
	 * @param url 页面的url
	 * @return
	 * @throws Exception
	 */
	public static TicketObject SETTicket(String url,String accessToken,String appId) throws Exception {
	    //1、获取jsapi_ticket  
	    String jsapi_ticket = getTicket(accessToken);  
	    //2、时间戳和随机字符串  
	    String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串  
	    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳  
	    //3、将参数排序并拼接字符串  
	    String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;  
	     
	    //4、将字符串进行sha1加密  
	    String signature =SHA1(str);  
	    TicketObject ticket = new TicketObject(noncestr, timestamp, signature);
		ticket.setAppId(appId);
	    return ticket;
	}
	
	
	/**
	 * 获取TicketObject
	 * @param map
	 * @param mapKey 
	 * @param url 需要签名的url
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static synchronized TicketObject getTicketObject(Map<String,TicketObject> map,String mapKey,String url,String accessToken,String appId) throws Exception{
		if(map.get(mapKey) == null){
			//刷新OR重新签名
			TicketObject ticketObject = JSSDKTiket.SETTicket(url,accessToken,appId);
			map.put(mapKey, ticketObject);
			System.out.println("--------------------------------------");
			System.out.println("--------------------------------------");
			log.info("------------ [log.info] Refresh the signature ------------" + map.get(mapKey));
			System.out.println("--------------------------------------");
			System.out.println("--------------------------------------");
		}else {
			System.out.println("--------------------------------------");
			System.out.println("--------------------------------------");
			log.info("------------ [log.info] Obtaining an existing signature ------------" + map.get(mapKey));
			System.out.println("--------------------------------------");
			System.out.println("--------------------------------------");
			return map.get(mapKey);
		}
       return map.get(mapKey);  
	}
	
	
	/**
	 * 配置网页JSSDK ticket数据
	 * @param url 需要签名的链接
	 * @param mapKey 保存的key
	 * @param delayTime JSSDK ticket刷新时间(delayTime的值需要小于2小时 比如7000s 7000 *1000)
	 */
	public static void reqTicket(HttpServletRequest req,String menuId,String url,String mapKey,long delayTime){
		Map<String,TicketObject> map = MapTicket.getInstance().getMap();
		TicketObject ticket;
		try {
			String key = mapKey;
			//截取签名key后面&from=0&isappinstalled=0部分
			String[] array = mapKey.split("&from");
			if(array.length > 0) {
				key = array[0];
			}
			//读取Map中的参数
			MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
			Map<String, String> mapWechatMsg = mapPublicNumber.getMap();
			if(mapWechatMsg != null && mapWechatMsg.size() == 0){
				System.out.println("map not data");
				JobPublicAccount.ToRefreshMapJobPublicAccount();
			}
			String accessToken = mapWechatMsg.get("accessToken" + menuId);
			String appId = mapWechatMsg.get("appId" + menuId);

			//进行签名
			ticket = JSSDKTiket.getTicketObject(map, key, url,accessToken,appId);
			req.setAttribute("appId", ticket.getAppId());
			req.setAttribute("noncestr", ticket.getNoncestr());
			req.setAttribute("signature", ticket.getSignature());
			req.setAttribute("timestamp", ticket.getTimestamp());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 配置网页JSSDK ticket数据
	 * @param url 需要签名的链接
	 * @param mapKey 保存的key
	 */
	public static Map<String,String> reqTicketInterface(String accessToken,String appId,String url,String mapKey){
		Map<String,String> mapReturn = new HashMap<String, String>();//返回的map集合，包含appId,noncestr,signature和timestamp
		Map<String,TicketObject> map = MapTicket.getInstance().getMap();
		TicketObject ticket;
		try {
			String key = mapKey;
			//截取签名key后面&from=0&isappinstalled=0部分
//			String[] array = mapKey.split("&from");
//			if(array.length > 0) {
//				key = array[0];
//			}
			//进行签名
			ticket = JSSDKTiket.getTicketObject(map, key, url,accessToken,appId);
			mapReturn.put("appId",ticket.getAppId());
			mapReturn.put("noncestr",ticket.getNoncestr());
			mapReturn.put("signature",ticket.getSignature());
			mapReturn.put("timestamp",ticket.getTimestamp());


			//获取消息队列
//			queue.offer(new Message(3, key, delayTime));//消息id 3表示JSSDK ticket
//			log.info("------------ [Queue Message] Refresh the JSSDK Ticket to Join The Queue "+delayTime+" After Milliseconds ：key = " + key  + "------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapReturn;
	}

	/**
	 * 配置网页JSSDK ticket， 需要自己手动刷新ticket（消息队列）
	 * @param accessToken
	 * @param url 需要签名的链接
	 * @param mapKey 保存的key
	 * @return 返回 json格式的签名
	 */
	public static String arrayTicket(String accessToken,String appId,String url,String mapKey){
		String json = null;
		Map<String,TicketObject> map = MapTicket.getInstance().getMap();
		TicketObject ticket;
		try {
			String key = mapKey;
			//截取签名key后面&from=0&isappinstalled=0部分
			String[] array = mapKey.split("&from");
			if(array.length > 0) {
				key = array[0];
			}
			//进行签名
			ticket = JSSDKTiket.getTicketObject(map, key, url,accessToken,appId);
			json = JSONUtils.toJSONString(ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
}
