package com.xiaoshu.wechat.menu;

/**
 * 复合类型的按钮
 * @author XGB
 */
public class ComplexButton extends Button {

	/**
	 * 复合类型按钮包含的按钮数组
	 */
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}
