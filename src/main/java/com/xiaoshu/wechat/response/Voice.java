package com.xiaoshu.wechat.response;

/**
 * 语音model
 * @author XGB
 */
public class Voice {

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
	

	public Voice() {
		super();
	}

	public Voice(String mediaId) {
		super();
		MediaId = mediaId;
	}

	@Override
	public String toString() {
		return "Voice [MediaId=" + MediaId + "]";
	}
	
	
}
