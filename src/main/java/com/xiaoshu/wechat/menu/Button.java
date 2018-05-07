package com.xiaoshu.wechat.menu;

/**
 * 按钮的基类
 * @author XGB
 */
public class Button {

	/**
	 * 按钮名称
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Button [name=" + name + "]";
	}
	
}
