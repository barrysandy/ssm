package com.xiaoshu.wechat.pojo;

/**
 * 保存微信签名、调用JS的参数
 * @author XGB
 *
 */
public class TicketObject {
	/**
	 * 签名的appid
	 */
	private String appId ;

	/**
	 * 签名的字符串
	 */
	private String noncestr;

	/**
	 * 签名的时间戳
	 */
	private String timestamp;

	/**
	 * 签名
	 */
	private String signature;
	
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	

	@Override
	public String toString() {
		return "TicketOverall [appId=" + appId + ", noncestr=" + noncestr
				+ ", signature=" + signature + ", timestamp=" + timestamp + "]";
	}

	public TicketObject(String appId, String noncestr, String timestamp,
			String signature) {
		super();
		this.appId = appId;
		this.noncestr = noncestr;
		this.timestamp = timestamp;
		this.signature = signature;
	}

	public TicketObject(String noncestr, String timestamp, String signature) {
		super();
		this.noncestr = noncestr;
		this.timestamp = timestamp;
		this.signature = signature;
	}

	public TicketObject() {
		super();
	}

	
	
	
}
