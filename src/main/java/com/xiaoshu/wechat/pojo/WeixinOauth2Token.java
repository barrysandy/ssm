package com.xiaoshu.wechat.pojo;

/**
 * 网页授权信息
 * @author XGB
 */
public class WeixinOauth2Token {
	/**
	 * 网页授权接口调用凭证
	 */
	private String access_token;

	/**
	 * 凭证有效时长
	 */
	private int expires_in;

	/**
	 * 用于刷新凭证
	 */
	private String refresh_token;

	/**
	 * 用户标识
	 */
	private String openId;

	/**
	 * 用户授权作用域
	 */
	private String scope;

	/**
	 * 用户unionid
	 */
	private String unionid;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String accessToken) {
		access_token = accessToken;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expiresIn) {
		expires_in = expiresIn;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refreshToken) {
		refresh_token = refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public WeixinOauth2Token(String accessToken, int expiresIn,
			String refreshToken, String openId, String scope, String unionid) {
		super();
		access_token = accessToken;
		expires_in = expiresIn;
		refresh_token = refreshToken;
		this.openId = openId;
		this.scope = scope;
		this.unionid = unionid;
	}

	public WeixinOauth2Token() {
		super();
	}

	@Override
	public String toString() {
		return "WeixinOauth2Token [access_token=" + access_token
				+ ", expires_in=" + expires_in + ", openId=" + openId
				+ ", refresh_token=" + refresh_token + ", scope=" + scope
				+ ", unionid=" + unionid + "]";
	}

		
}
