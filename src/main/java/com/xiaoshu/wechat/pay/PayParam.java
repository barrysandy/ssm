package com.xiaoshu.wechat.pay;

/**
 * 支付POJO类
 * @author xugongbin
 * @Date 2017-7-21 13:25
 *
 */
public class PayParam {

	private String appId;//公众号名称，由商户传入
	private String timeStamp;//时间戳，自1970年以来的秒数     
	private String nonceStr;//随机串
	private String packageValue;
	private String signType;//签名方式 MD5
	private String paySign;//微信签名
	private String prepayId; //微信预支付id

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getPackageValue() {
		return packageValue;
	}

	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public PayParam() { }

	public PayParam(String appId, String timeStamp, String nonceStr, String packageValue, String signType, String paySign, String prepayId) {
		this.appId = appId;
		this.timeStamp = timeStamp;
		this.nonceStr = nonceStr;
		this.packageValue = packageValue;
		this.signType = signType;
		this.paySign = paySign;
		this.prepayId = prepayId;
	}

	@Override
	public String toString() {
		return "PayParam{" +
				"appId='" + appId + '\'' +
				", timeStamp='" + timeStamp + '\'' +
				", nonceStr='" + nonceStr + '\'' +
				", packageValue='" + packageValue + '\'' +
				", signType='" + signType + '\'' +
				", paySign='" + paySign + '\'' +
				", prepayId='" + prepayId + '\'' +
				'}';
	}
}
