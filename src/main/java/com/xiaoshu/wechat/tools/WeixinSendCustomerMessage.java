package com.xiaoshu.wechat.tools;


import com.xiaoshu.tools.ToolsHttpRequest;

public class WeixinSendCustomerMessage {

	/**
	 * 发送文本消息给用户
	 * @param access_token
	 * @param toUser
	 * @param content
	 * @return
	 */
	public static String sendTextMessage(String access_token,String toUser,String content) {
		String result = "";
        String json = "{\"touser\": \""+toUser+"\",\"msgtype\": \"text\", \"text\": {\"content\": \""+content+"\"}}";
        System.out.println("json: " + json);
        //发送文本  
        String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+access_token;  
        try {  
            result = ToolsHttpRequest.sendPostByUTF8(action, json);  
            System.out.println(result);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
		return result;
	}
	
	public static String sendTempletMessage(String access_token,String template_id,String toUser,String name,String remark) {
		String result = "";
        String json = "{\"touser\": \""+toUser+"\",\"template_id\": \""+template_id+"\",\"url\":\"http://weixin.qq.com/download\",\"data\":{\"name\": {\"value\":\""+name+"\",\"color\":\"#173177\"},\"remark\":{\"value\":\""+remark+"\",\"color\":\"#173177\"}}}";
        System.out.println("json: " + json);
        //发送模版 
        String action = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;  
        try {  
            result = ToolsHttpRequest.sendPostByUTF8(action, json);  
            System.out.println(result);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
		return result;
	}

}
