package com.xiaoshu.wechat.pojo;

import java.util.TreeMap;

/**
 * 微信模板消息类
 * @author XGB
 */
public class WechatTemplateMsg {

    /**
     * 接收者openid
     */
    private String touser;

    /**
     * 模板ID
     */
    private String template_id;
  
    //@NotRequire//只是个标识
    /**
     * 模板跳转链接
     */
    private String url;
  
    // "miniprogram":{ 未加入  
    // "appid":"xiaochengxuappid12345",  
    // "pagepath":"index?foo=bar"  
    // },  
  
    private TreeMap<String, TreeMap<String, String>> data; //data数据  
  
    public String getTouser() {  
        return touser;  
    }  
  
    public void setTouser(String touser) {  
        this.touser = touser;  
    }  
  
    public String getTemplate_id() {  
        return template_id;  
    }  
  
    public void setTemplate_id(String template_id) {  
        this.template_id = template_id;  
    }  
  
    public String getUrl() {  
        return url;  
    }  
  
    public void setUrl(String url) {  
        this.url = url;  
    }  
  
    public TreeMap<String, TreeMap<String, String>> getData() {  
        return data;  
    }  
  
    public void setData(TreeMap<String, TreeMap<String, String>> data) {  
        this.data = data;  
    }  
  
    /** 
     * 参数 
     * @param value 
     * @param color 可不填 
     * @return 
     */  
    public static TreeMap<String, String> item(String value, String color) {  
        TreeMap<String, String> params = new TreeMap<String, String>();  
        params.put("value", value);  
        params.put("color", color);  
        return params;  
    }  
}  