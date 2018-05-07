package com.xiaoshu.wechat.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.xiaoshu.job.JobPublicAccount;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaoshu.wechat.api.WeChatAPI;
import com.xiaoshu.wechat.pojo.AccessToken;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeixinUtil {

	private static Logger log = LoggerFactory.getLogger(JobPublicAccount.class);

	/**
	 * 获取AccessToken
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static AccessToken getAccessToken(String appid,String secret) throws Exception {
		if(appid != null && secret != null){
			if(!"".equals(appid) && !"".equals(secret)){
				AccessToken accessToken = null;
				JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
				String requestUrl = WeChatAPI.GET_ACCESSTOKEN_URL.replace("APPID" , appid).replace("APPSECRET" , secret);
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(requestUrl);
				HttpResponse res = client.execute(get);
				String responseContent = null; // 响应内容
				HttpEntity entity = res.getEntity();
				responseContent = EntityUtils.toString(entity, "UTF-8");
				JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
				log.info("------------ [log.info System Message] : GET AccessToken APPID = " + appid +" ------------");
				log.info("------------ [log.info System Message] : resp json: " + json +" ------------");
				System.out.println("------------ [log.info System Message] : resp json: " + json +" ------------");
				// 如果请求成功
				if (null != json) {
					try {
						if(json.get("access_token") != null && !"".equals(json.get("access_token"))){
							String accessJson = json.get("access_token").getAsString();
							accessToken = new AccessToken();
							if(accessJson != null && !"".equals(accessJson)){
								accessToken.setAccessToken(accessJson);
							}
							accessToken.setExpiresIn(json.get("expires_in").getAsInt());
						}

					} catch (Exception e) {
						e.printStackTrace();
						accessToken = null;
						// 获取token失败
						log.info("------------ [log.info System Message] : 获取token失败 ------------");
					}
				}
				return accessToken;
			}
		}
		return null;
	}
	

	
	/**
	 * Http请求
	 * @param requestUrl 请求URL地址
	 * @param requestMethod 请求方法GET/POST
	 * @param outputStr 请求参数
	 * @return
	 */
	public static String httpRequest(String requestUrl , String requestMethod , String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL" , "SunJSSE");
			sslContext.init(null , tm , new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream , "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
//			jsonObject = JSONObject.fromObject(buffer.toString());
			return buffer.toString();
		} catch (ConnectException ce) {
			log.info("------------ [log.info System Message] : Weixin server connection timed out. ------------");
		} catch (Exception e) {
			log.info("------------ [log.info System Message] : Weixin server error. ------------");
		}
//		return jsonObject;
		return null;
	}

}