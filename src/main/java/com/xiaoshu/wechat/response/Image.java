package com.xiaoshu.wechat.response;

/**
 * 图片model
 * @author XGB
 */
public class Image {

	/**
	 * 媒体文件id
	 */
	private String MediaId;
	

	public String getMediaId() {
		return MediaId;
	}


	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}


	public Image() {
		super();
	}


	public Image(String mediaId) {
		super();
		MediaId = mediaId;
	}


	@Override
	public String toString() {
		return "Image [MediaId=" + MediaId + "]";
	}
	
}
