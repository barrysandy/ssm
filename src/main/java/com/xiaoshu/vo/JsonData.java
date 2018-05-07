package com.xiaoshu.vo;


import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsString;

/**
 * 访问接口返回的json标准数据类型
 * @author: XGB
 * @date: 2018-05-02 09:46
 */
public class JsonData {
	public static final String STATUS_OK = "ok";
	public static final String STATUS_FAIL = "fail";

	private String status; //状态 STATUS_OK  STATUS_FAIL
	private String data; //数据
	private String descM;//描述

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescM() {
		return descM;
	}

	public void setDescM(String descM) {
		this.descM = descM;
	}
	public JsonData() { }

	public JsonData(String status, String data, String descM) {
		this.status = status;
		this.data = data;
		this.descM = descM;
	}

	@Override
	public String toString() {
		return "JsonData{" +
				"status='" + status + '\'' +
				", data='" + data + '\'' +
				", descM='" + descM + '\'' +
				'}';
	}

	/** 对象 转 json */
	public static String getJson(JsonData jsonData){
		if(jsonData != null) {
			String json = JSONUtils.toJSONString(jsonData);
			json = ToolsString.getStrRemoveBracket(json);
			return json;
		}else {
			return null;
		}
	}

	/** json 转 对象 */
	public static JsonData getObject(String json){
		if(json != null) {
			try {
				JsonData jsonData = JSONUtils.toBean(json, JsonData.class);
				return jsonData;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}else {
			return null;
		}
	}


}