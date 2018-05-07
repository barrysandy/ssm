package com.xiaoshu.wechat.pojo;

/**
 * 微信客服类
 * @author XGB
 */
public class Customer {

	/**
	 *
	 */
	private long kf_id;
	/**
	 *
	 */
	private String kf_nick;
	/**
	 *
	 */
	private String kf_wx;
	/**
	 *
	 */
    private String kf_account;
	/**
	 * 客服头像
	 */
    private String kf_headimgurl;
	public long getKf_id() {
		return kf_id;
	}
	public void setKf_id(long kfId) {
		kf_id = kfId;
	}
	public String getKf_nick() {
		return kf_nick;
	}
	public void setKf_nick(String kfNick) {
		kf_nick = kfNick;
	}
	public String getKf_wx() {
		return kf_wx;
	}
	public void setKf_wx(String kfWx) {
		kf_wx = kfWx;
	}
	public String getKf_account() {
		return kf_account;
	}
	public void setKf_account(String kfAccount) {
		kf_account = kfAccount;
	}
	public String getKf_headimgurl() {
		return kf_headimgurl;
	}
	public void setKf_headimgurl(String kfHeadimgurl) {
		kf_headimgurl = kfHeadimgurl;
	}
	public Customer(long kfId, String kfNick, String kfWx, String kfAccount,
			String kfHeadimgurl) {
		super();
		kf_id = kfId;
		kf_nick = kfNick;
		kf_wx = kfWx;
		kf_account = kfAccount;
		kf_headimgurl = kfHeadimgurl;
	}
	public Customer() {
		super();
	}
	@Override
	public String toString() {
		return "Customer [kf_account=" + kf_account + ", kf_headimgurl="
				+ kf_headimgurl + ", kf_id=" + kf_id + ", kf_nick=" + kf_nick
				+ ", kf_wx=" + kf_wx + "]";
	}
 
}
