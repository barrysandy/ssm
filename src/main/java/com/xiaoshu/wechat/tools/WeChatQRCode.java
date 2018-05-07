package com.xiaoshu.wechat.tools;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xiaoshu.job.JobPublicAccount;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.tools.single.MapPublicNumber;
import com.xiaoshu.wechat.api.WeChatAPI;
import com.xiaoshu.wechat.pojo.WeixinQRCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 参数二维码
 * @author XGB
 *
 */
public class WeChatQRCode {
	private static Logger log = LoggerFactory.getLogger(WeChatQRCode.class);



	// 临时二维码
	private final static String QR_SCENE = "QR_SCENE";
	// 永久二维码
	private final static String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";
	// 永久二维码(字符串)
	private final static String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";
	// 创建二维码
	private  String create_ticket_path = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
	// 通过ticket换取二维码
	private String showqrcode_path = "https://mp.weixin.qq.com/cgi-bin/showqrcode";

	/**
	 * 创建临时带参数二维码
	 * @param accessToken
	 * @expireSeconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
	 * @param sceneId 场景Id
	 * @return
	 */
	public String createTempTicket(String accessToken, String expireSeconds, int sceneId) {
		WeixinQRCode wxQRCode = null;
		TreeMap<String,String> params = new TreeMap<String,String>();
		params.put("access_token", accessToken);
		Map<String,Integer> intMap = new HashMap<String,Integer>();
		intMap.put("scene_id",sceneId);
		Map<String,Map<String,Integer>> mapMap = new HashMap<String,Map<String,Integer>>();
		mapMap.put("scene", intMap);
		//
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("expire_seconds", expireSeconds);
		paramsMap.put("action_name", QR_SCENE);
		paramsMap.put("action_info", mapMap);
		String data = new Gson().toJson(paramsMap);
		data = HttpRequestUtil.HttpsDefaultExecute(HttpRequestUtil.POST_METHOD,create_ticket_path,params,data);
		try {
			wxQRCode = new Gson().fromJson(data, WeixinQRCode.class);
		} catch (JsonSyntaxException e) {
			wxQRCode = null;
			e.printStackTrace();
		}
		return wxQRCode==null?null:wxQRCode.getTicket();

	}







	/**
	 * 创建带参二维码 整数
	 * @param menuId 获取accessToken 的公众号标识
	 * @param expire_seconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
	 * @param action_name 二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值
	 * @param scene_id 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @return
	 */
	public static String getParametricQRCode(String menuId,int expire_seconds,String action_name,String scene_id){
		//返回的json
		String json = null;
		try{

			//读取Map中的token
			MapPublicNumber mapPublicNumber = MapPublicNumber.getInstance();
			Map<String, String> map = mapPublicNumber.getMap();
			if(map != null && map.size() == 0){
				System.out.println("map not data");
				JobPublicAccount.ToRefreshMapJobPublicAccount();
			}
			String accessToken = map.get("accessToken" + menuId);
			System.out.println("-----------------------accessToken----------------------------");
			System.out.println("---------------------------------------------------");
			System.out.println("---------------------------------------------------menuId: " + menuId);
			System.out.println("---------------------------------------------------accessToken: " + accessToken);
			System.out.println("---------------------------------------------------");
			if(accessToken != null){
				if(!"".equals(accessToken)){
					String url = WeChatAPI.CREATE_QR_CODE.replace("TOKEN",accessToken);
					String param = "{\n" +
							"    \"expire_seconds\": "+expire_seconds+",\n" +
							"    \"action_name\": \""+action_name+"\",\n" +
							"    \"action_info\": {\n" +
							"        \"scene\": {\n" +
							"            \"scene_str\": "+ scene_id +"\n" +
							"        }\n" +
							"    }\n" +
							"}";
					System.out.println("-----------------------url----------------------------");
					System.out.println("---------------------------------------------------");
					System.out.println("---------------------------------------------------url: " + url);
					System.out.println("---------------------------------------------------");
					System.out.println("---------------------------------------------------");
					System.out.println("-----------------------getParametricQRCode----------------------------");
					System.out.println("---------------------------------------------------");
					System.out.println("---------------------------------------------------scene_id: " + scene_id);
					System.out.println("---------------------------------------------------");
					System.out.println("---------------------------------------------------");
					json = ToolsHttpRequest.sendPost(url,param);
					System.out.println("-----------------------json----------------------------");
					System.out.println("---------------------------------------------------");
					System.out.println("---------------------------------------------------json: " + json);
					System.out.println("---------------------------------------------------");
					System.out.println("---------------------------------------------------");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return json;
	}


	/**
	 * 获取二维码ticket后，通过ticket换取二维码图片展示
	 * @param ticket
	 * @return
	 */
	public static String showQrcode(String ticket){
		String path = null ;
		Map<String,String> params = new TreeMap<String,String>();
		params.put("ticket", HttpRequestUtil.urlEncode(ticket, HttpRequestUtil.DEFAULT_CHARSET));
		try {
			path =  HttpRequestUtil.setParmas(params,WeChatAPI.SHOW_QR_CODE,"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 获取二维码ticket后，通过ticket换取二维码图片
	 * @param ticket
	 * @param savePath  保存的路径,例如 F:\\test\test.jpg
	 * @return      Result.success = true 表示下载图片下载成功
	 */
	public static WeiXinResult showQrcode(String ticket,String savePath) throws Exception{
		WeiXinResult result = null;
		try {
			TreeMap<String,String> params = new TreeMap<String,String>();
			params.put("ticket", HttpRequestUtil.urlEncode(ticket, HttpRequestUtil.DEFAULT_CHARSET));
			result = HttpRequestUtil.downMeaterMetod(params,HttpRequestUtil.GET_METHOD,WeChatAPI.SHOW_QR_CODE_IMAGE,savePath);
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}


}
