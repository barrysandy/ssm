package com.xiaoshu.wechat.robot;

import com.xiaoshu.wechat.response.TextMessage;
import com.xiaoshu.wechat.response.NewsMessage;
import com.xiaoshu.wechat.response.MusicMessage;
import com.xiaoshu.wechat.response.VoiceMessage;
import com.xiaoshu.wechat.response.VideoMessage;
import com.xiaoshu.wechat.response.ImageMessage;

import com.xiaoshu.wechat.response.Music;
import com.xiaoshu.wechat.response.Voice;
import com.xiaoshu.wechat.response.Video;
import com.xiaoshu.wechat.response.Image;
import com.xiaoshu.wechat.response.Article;

import java.util.List;
import java.util.Date;

import com.xiaoshu.wechat.tools.MessageUtil;

/**
 * 公众号
 * @author XGB
 */
public class MessageResponse {
	
	/**
	 * 回复文本消息
	 * @param fromUserName
	 * @param toUserName
	 * @param respContent
	 * @return
	 */
	public static String getTextMessage(String fromUserName , String toUserName , String respContent) {
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setContent(respContent);
		return MessageUtil.textMessageToXml(textMessage);
	}
	
	/**
	 * 创建图文消息
	 * @param fromUserName
	 * @param toUserName
	 * @param articleList
	 * @return
	 */
	public static String getNewsMessage(String fromUserName , String toUserName , List<Article> articleList) {
		
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		
		// 设置图文消息个数
		newsMessage.setArticleCount(articleList.size());
		// 设置图文消息包含的图文集合
		newsMessage.setArticles(articleList);
		// 将图文消息对象转换成xml字符串
		return MessageUtil.newsMessageToXml(newsMessage);
	}
	
	/**
	 * 创建语音消息
	 * @param fromUserName
	 * @param toUserName
	 * @param music
	 * @return
	 */
	public static String getMusicMessage(String fromUserName , String toUserName , Music music) {
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setToUserName(toUserName);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setFromUserName(fromUserName);
		musicMessage.setMusic(music);
		musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
		return MessageUtil.musicMessageToXml(musicMessage);
	}
	
	
	/**
	 * 创建语音消息
	 * @param fromUserName
	 * @param toUserName
	 * @param music
	 * @return
	 */
	public static String getVoiceMessage(String fromUserName , String toUserName , Voice voice) {
		
		VoiceMessage voiceMessage = new VoiceMessage();
		voiceMessage.setToUserName(toUserName);
		voiceMessage.setCreateTime(new Date().getTime());
		voiceMessage.setFromUserName(fromUserName);
		voiceMessage.setVoice(voice);
		voiceMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_VOICE);
		return MessageUtil.voiceMessageToXml(voiceMessage);
	}
	
	
	/**
	 * 创建视频消息
	 * @param fromUserName
	 * @param toUserName
	 * @param video
	 * @return
	 */
	public static String getVideoMessage(String fromUserName , String toUserName , Video video) {
		VideoMessage videoMessage  = new VideoMessage();
		videoMessage.setCreateTime(new Date().getTime());
		videoMessage.setFromUserName(fromUserName);
		videoMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_VOICE);
		videoMessage.setToUserName(toUserName);
		videoMessage.setVideo(video);
		return MessageUtil.videoMessageToXml(videoMessage);
	}
	
	/**
	 * 创建图片消息
	 * @param fromUserName
	 * @param toUserName
	 * @param Image
	 * @return
	 */
	public static String getImageMessage(String fromUserName , String toUserName , Image image) {
		ImageMessage imageMessage  = new ImageMessage();
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setFromUserName(fromUserName);
		imageMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_IMAGE);
		imageMessage.setToUserName(toUserName);
		imageMessage.setImage(image);
		return MessageUtil.imageMessageToXml(imageMessage);
	}
	
}