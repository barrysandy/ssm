package com.xiaoshu.vo;


/**
 * 所属类别:实体类 <br/> 
 * 用途: Jssdk实体类<br/>
* @author: XGB
* @date: 2018-03-02 13:21
 */
public class Jssdk{
	private String appId;//签名的appId
	private String noncestr;//签名的noncestr
	private String signature;//签名的signature
	private String timestamp;//签名的timestamp

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Jssdk() {
	}

	public Jssdk(String appId, String noncestr, String signature, String timestamp) {
		this.appId = appId;
		this.noncestr = noncestr;
		this.signature = signature;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Jssdk{" +
				"appId='" + appId + '\'' +
				", noncestr='" + noncestr + '\'' +
				", signature='" + signature + '\'' +
				", timestamp='" + timestamp + '\'' +
				'}';
	}
}