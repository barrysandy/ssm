package com.xiaoshu.wechat.menu;

/**
 * click类型的按钮
 * @author XGB
 */
public class ClickButton extends Button {

	/**
	 * 按钮类型
	 */
	private String type;

	/**
	 * 按钮key
	 */
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}