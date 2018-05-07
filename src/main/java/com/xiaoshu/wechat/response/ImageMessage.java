package com.xiaoshu.wechat.response;

/**
 * 图片消息
 * @author XGB
 */
public class ImageMessage extends BaseMessage {

	/**
	 * 图片
	 */
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}

	@Override
	public String toString() {
		return "ImageMessage [Image=" + Image + "]";
	}
	
	
	
}
