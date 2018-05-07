package com.xiaoshu.wechat.request;

/**
 * 文本消息
 * @author XGB
 */
public class TextMessage extends BaseMessage {
	/**
	 * 消息内容
	 */
	private String Content;

	/**
	 * 消息内容
	 */
	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
