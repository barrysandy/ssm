package com.xiaoshu.tools;

import com.xiaoshu.api.Api;
import net.sf.json.JSONObject;

/**
 * 短信接口Tools
 * @author XGB
 *
 */
public class ToolsSendMessageService {
    /**
     * 发送短信
	 * @param mobile
	 * @param content
     * @return 发送的结果码
     */
	public static String sendMsgServer(String mobile,String content) {
		String resp = "";
		if(mobile != null && content != null){
			if(!"".equals(mobile) && !"".equals(mobile)){
				String param = "mobile=" + mobile + "&content=" + content;
				String json = ToolsHttpRequest.sendGet(Api.SEND_MSG, param);
				if(json != null){
					if(!"".equals(json)){
						JSONObject jsonObject = JSONObject.fromObject(json);
						resp = jsonObject.getString("key");
					}
				}
			}
		}
		return resp;
	}

}