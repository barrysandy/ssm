package com.xiaoshu.wechat.menu;

/**
 * view类型的按钮
 * @author XGB
 */
public class ViewButton extends Button {
	/**
	 * 视图按钮的类型
	 */
	private String type;
	/**
	 * 视图按钮的点击跳转URL
	 */
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
