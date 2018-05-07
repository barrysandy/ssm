package com.xiaoshu.wechat.messagehandle;

import com.xiaoshu.api.Api;
import com.xiaoshu.api.Set;
import com.xiaoshu.entity.*;
import com.xiaoshu.enumeration.EnumsAutomaticReplyType;
import com.xiaoshu.enumeration.EnumsWeChatMenuClickKey;
import com.xiaoshu.po.UserClickGroup;
import com.xiaoshu.po.UserClickGroupMap;
import com.xiaoshu.tools.*;
import com.xiaoshu.vo.CommodityVo;
import com.xiaoshu.vo.CommodityVoQRCode;
import com.xiaoshu.wechat.response.Article;
import com.xiaoshu.wechat.response.Voice;
import com.xiaoshu.wechat.robot.MessageResponse;
import com.xiaoshu.wechat.robot.Robot;
import com.xiaoshu.wechat.tools.GetKey;
import com.xiaoshu.wechat.tools.MessageUtil;
import com.xiaoshu.wechat.tools.WeChatSaveAndUpdateUserMsg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xiaoshu.wechat.response.Video;
import com.xiaoshu.wechat.response.Image;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信消息服务工具类
 * @name: MessageServiceUtils
 * @author: XGB
 * @date: 2018-01-17 10:50
 */
public class MessageServiceUtils {
	
	/**
	 * 处理文字信息
	 */
	public static String text(String menuId ,HttpServletRequest request,Map<String,String> requestMap,
			String fromUserName,String toUserName,String msgType,String content,String respMessage,String respContent){
		try{
	//		String contentStr = requestMap.get("Content");//消息内容
			String contentStr = content;
			content = ToolsASCIIChang.stringToAscii(content);
			respContent = Robot.RobotAnser(menuId,"AUTO_REPLY" );//默认回复内容

			//微信聊天机器人
			if(content != null){
				if("".equals(respContent)|| respContent == null){
					return MessageResponse.getTextMessage(fromUserName , toUserName , "该公众号暂时无法回复，请稍后再试！");
				}
				else{
					//TODO 寻找当前menuId 下的signSession类型的并且正在进行的活动列表，匹配活动列表的关键字是否是活动的描述，如果匹配则确定当前的活动
					//用于判断活动的时间是否在当前时间范围内
//					String nowTime = ToolsDate.getStringDate(ToolsDate.simpleSecond);
					/** 获取匹配的会话活动 */
					String url = Set.SYSTEM_URL + "activityinterface/getListJson";
					String params = "menuId=" + menuId;
					String json = ToolsHttpRequest.sendGet(url,params);
					if(json != null && !"[]".equals(json)){
						//TODO 处理会话报名
						String result = signSession( json, contentStr, fromUserName, toUserName, menuId);
						if(result != null){
							return result;
						}
					}
					String strType = GetKey.getStr(content)[0];//匹配关键字类型
					String strValue = GetKey.getStr(content)[1];//匹配关键字值
					//String strValues = GetKey.getStr(content)[1];
					if("key".equals(strType)){//非接口直接查询数据库的关键字
						respContent = Robot.RobotAnser(menuId,strValue);//到自己的数据库匹配关键字
                        if("null".equals(respContent) || respContent == null){//没有查询到关键字，需要回复默认消息AUTO_REPLY
							return AutoReply(menuId,toUserName,fromUserName);
                        }
						else if(respContent.indexOf(":\"text\"") != -1){//返回类型是text
							KeyWords keyWord = (KeyWords) JSONUtils.toBean(respContent, KeyWords.class);
							respContent = keyWord.getValuess();
							return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
						}
						else if(respContent.indexOf(":\"art\"") != -1){//返回类型是art
							List<Article> articleList = new ArrayList<Article>();
							KeyWords keyWord = (KeyWords) JSONUtils.toBean(respContent, KeyWords.class);
							String val = keyWord.getValuess();
							int current = Integer.parseInt(val);
							String respJson = Robot.robotGetArtByPeriod(menuId,current);//到自己的数据库匹配文章
							//发送文章消息
							return sendArt(articleList, respJson, fromUserName, toUserName);
						}
						else if(respContent.indexOf(":\"voice\"") != -1){//返回类型是voice
							KeyWords keyWord = (KeyWords) JSONUtils.toBean(respContent, KeyWords.class);
							Voice voice = new Voice();
							voice.setMediaId(keyWord.getValuess());
							return MessageResponse.getVoiceMessage(toUserName, fromUserName, voice);
						}
						else if(respContent.indexOf(":\"video\"") != -1){//返回类型是video
							KeyWords keyWord = (KeyWords) JSONUtils.toBean(respContent, KeyWords.class);
							Video video = new Video();
							video.setMediaId(keyWord.getValuess());
							return MessageResponse.getVideoMessage(toUserName, fromUserName, video);
						}
						else if(respContent.indexOf(":\"image\"") != -1){//返回类型是image
							KeyWords keyWord = (KeyWords) JSONUtils.toBean(respContent, KeyWords.class);
							System.out.println("======================>>keyWord: " + keyWord);
							Image image = new Image();
							image.setMediaId(keyWord.getValuess());
							return MessageResponse.getImageMessage(toUserName, fromUserName, image);
						}
						else{
							return AutoReply(menuId,toUserName,fromUserName);
						}
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
	}
	
	
	/**
	 * 处理事件推送
	 */
	public static String event(String menuId,HttpServletRequest request,Map<String,String> requestMap,String fromUserName,String toUserName,String msgType,String content,String respMessage,String respContent){
		String eventType = requestMap.get("Event");// 事件类型
		if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 关注公众号事件
			respContent = Robot.RobotAnser(menuId,"ATTENTION_REPLY" );//首次关注默认回复内容
			String[] strArray = null;
	        strArray = respContent.split("#"); //拆分字符为"," ,然后把结果交给数组strArray 
			StringBuffer replyMsg = new StringBuffer();
			replyMsg.append(strArray[0]);
			for (int i = 1; i < strArray.length; i++) {
				replyMsg.append("\n"+strArray[i]);
			}
			//保存用户/更新数据库
			WeChatSaveAndUpdateUserMsg.handleUser(fromUserName,menuId);
			return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
		}
		return MessageResponse.getTextMessage(fromUserName, toUserName, respContent); 
		
	}
	
	/**
	 * 处理关注 订阅事件
	 */
	public static String subScribe(String menuId,HttpServletRequest request,Map<String,String> requestMap,
			String fromUserName,String toUserName,String msgType,String content,String respMessage,String respContent){
//		String eventKey = requestMap.get("EventKey");
		//保存用户/更新数据库
		WeChatSaveAndUpdateUserMsg.handleUser(fromUserName,menuId);
		String jsonStr = ToolsHttpRequest.sendGet(Set.SYSTEM_URL + "apiInterface/interfaceGetUserInfoByOpenid","openid=" + fromUserName);
		if(jsonStr != null){
			FocusedUserInfo focusedUserInfo = JSONUtils.toBean(jsonStr,FocusedUserInfo.class);
			if(focusedUserInfo != null){
				if(focusedUserInfo.getUnionid() != null){
					if(!"".equals(focusedUserInfo.getUnionid())){
						Map<String, UserClickGroup> map = UserClickGroupMap.getInstance().getMap();
						UserClickGroup userClickGroup = map.get(focusedUserInfo.getUnionid());
						if(userClickGroup != null){
//							String groupCode = userClickGroup.getGroupCode();
							String commodityId = userClickGroup.getClickId();
							String menuIdOther = userClickGroup.getCurrentMenuId();
							String shareGroupId = userClickGroup.getGroupId();
							List<Article> articleList = new ArrayList<Article>();
							String url = Set.SYSTEM_URL + "shop/groupCommodityDetailsInUser?menuId=" + menuIdOther + "&id=" + commodityId + "&shareUserId=-1&shareGroupId=" + shareGroupId + "&from=singlemessage&isappinstalled=0";
							String json = ToolsHttpRequest.sendGet(Api.GET_COMMODITY, "id=" + commodityId + "&shareGroupId=" + shareGroupId);
							CommodityVoQRCode commodityVoQRCode = JSONUtils.toBean(json,CommodityVoQRCode.class);
							Article article = new Article();
							article.setTitle(ToolsASCIIChang.asciiToString(commodityVoQRCode.getTitle()));
							article.setDescription(ToolsASCIIChang.asciiToString(commodityVoQRCode.getDescM()));
							article.setPicUrl(commodityVoQRCode.getImage());
							article.setUrl(url);
							articleList.add(article);
							//发送文章消息
							return MessageResponse.getNewsMessage(fromUserName, toUserName, articleList);
						}
					}
				}
			}
		}

		respContent = Robot.RobotAnser(menuId,"ATTENTION_REPLY" );//首次关注默认回复内容
		KeyWords keyWord = JSONUtils.toBean(respContent, KeyWords.class);
		if(keyWord != null){//设置了关键字
			respContent = keyWord.getValuess();
			if(EnumsAutomaticReplyType.TEXT.equals(keyWord.getTypess()) || "".equals(keyWord.getTypess())){//文本
				String[] strArray = null;
				strArray = keyWord.getValuess().split("#"); //拆分字符为"," ,然后把结果交给数组strArray
				StringBuffer replyMsg = new StringBuffer();
				replyMsg.append(strArray[0]);
				for (int i = 1; i < strArray.length; i++) {
					replyMsg.append("\n"+strArray[i]);
				}
				return MessageResponse.getTextMessage(fromUserName , toUserName , replyMsg.toString());
			}
			if(EnumsAutomaticReplyType.VOICE.equals(keyWord.getTypess())){//返回类型是voice
				Voice voice = new Voice();
				voice.setMediaId(keyWord.getValuess());
				return MessageResponse.getVoiceMessage(toUserName, fromUserName, voice);
			}
			if(EnumsAutomaticReplyType.VIDEO .equals(keyWord.getTypess())){//返回类型是video
				Video video = new Video();
				video.setMediaId(keyWord.getValuess());
				return MessageResponse.getVideoMessage(toUserName, fromUserName, video);
			}
			if(EnumsAutomaticReplyType.IMAGE.equals(keyWord.getTypess())){//返回类型是image
				Image image = new Image();
				image.setMediaId(keyWord.getValuess());
				return MessageResponse.getImageMessage(toUserName, fromUserName, image);
			}
			if(EnumsAutomaticReplyType.ART.equals(keyWord.getTypess())){
				List<Article> articleList = new ArrayList<Article>();
				String val = keyWord.getValuess();
				int artId = Integer.parseInt(val);
				String respJson = Robot.robotGetArtByPeriod(menuId,artId);//到自己的数据库匹配文章
				//发送文章消息
				return sendArt(articleList, respJson, fromUserName, toUserName);
			}

		}else{//没有设置关键字，需要进行默认消息处理
			return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
		}
		return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);

	}
	
	/**
	 * 处理按钮事件
	 */
	public static String click(String menuId,HttpServletRequest request,Map<String,String> requestMap,String fromUserName,String toUserName,String msgType,String content,String respMessage,String respContent){
		List<Article> articleList = new ArrayList<Article>();
		String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
		/**
		 * 点击本期精选按钮，获取最新一期的文章
		 */
		if(EnumsWeChatMenuClickKey.FINAL_ARTIICLE.equals(eventKey)){// Key is Final_article
			String respJson = Robot.robotGetFinalArt(menuId);//到自己的数据库匹配关键字
			//发送文章消息
			return sendArt(articleList, respJson, fromUserName, toUserName);
		}
		/**
		 * 点击获取多少期按钮，获取第N期的文章
		 * N为点击key的后面数字部分
		 */
		if(eventKey.contains(EnumsWeChatMenuClickKey.CURRET_ARTIICLE)){// Key is Current_article
			//截取按钮事件的数字部分并转换为int类型  即Current_article2 转换后为2
            String lastChar = eventKey.substring(EnumsWeChatMenuClickKey.CURRET_ARTIICLE.length());
            if(lastChar != null && !"".equals(lastChar)){
                int current = Integer.parseInt(lastChar);
                String respJson = Robot.robotGetArtByPeriod(menuId,current);//到自己的数据库匹配文章
                //发送文章消息
                return sendArt(articleList, respJson, fromUserName, toUserName);
            }
		}
		return MessageResponse.getTextMessage(fromUserName, toUserName, respContent);


	}

	/**
	 * 封装文章消息
	 * @param articleList 文章列表
	 * @param respJson 文章列表json
	 * @param fromUserName 发送者
	 * @param toUserName 接收者
	 * @return
	 */
	public static String sendArt(List<Article> articleList,String respJson,String fromUserName,String toUserName){
		List<Art> artList = JSONUtils.toList(respJson, Art.class);
		Iterator<Art> iterator = artList.iterator();
		while(iterator.hasNext()){
			Art currentArt = iterator.next();
			String img = currentArt.getArtImg();
			Article art = new Article();
			art.setDescription(currentArt.getArtSummary());
			art.setTitle(currentArt.getArtTitle());
			art.setPicUrl(img);
			art.setUrl(currentArt.getArtUrl());
			articleList.add(art);
		}

		return MessageResponse.getNewsMessage(fromUserName, toUserName, articleList);
	}

    /**
     * 公众号默认回复
     */
	public static String AutoReply(String menuId,String fromUserName,String toUserName){
		String auto = Robot.RobotAnser(menuId,"AUTO_REPLY");//到自己的数据库匹配关键字
        if(auto != null){
            if(!"".equals(auto)){
                KeyWords keyWords = (KeyWords) JSONUtils.toBean(auto, KeyWords.class);
                if(keyWords != null){
					if(EnumsAutomaticReplyType.TEXT.equals(keyWords.getTypess())){//返回类型是text
						return MessageResponse.getTextMessage(toUserName , fromUserName , keyWords.getValuess());
					}
					if(EnumsAutomaticReplyType.VOICE.equals(keyWords.getTypess())){//返回类型是voice
						Voice voice = new Voice();
						voice.setMediaId(keyWords.getValuess());
						return MessageResponse.getVoiceMessage(toUserName, fromUserName, voice);
					}
					if(EnumsAutomaticReplyType.VIDEO.equals(keyWords.getTypess())){//返回类型是video
						Video video = new Video();
						video.setMediaId(keyWords.getValuess());
						return MessageResponse.getVideoMessage(toUserName, fromUserName, video);
					}
					if(EnumsAutomaticReplyType.IMAGE.equals(keyWords.getTypess())){//返回类型是image
						Image image = new Image();
						image.setMediaId(keyWords.getValuess());
						return MessageResponse.getImageMessage(toUserName, fromUserName, image);
					}
					if(EnumsAutomaticReplyType.ART.equals(keyWords.getTypess())){
						List<Article> articleList = new ArrayList<Article>();
						String val = keyWords.getValuess();
						int artId = Integer.parseInt(val);
						String respJson = Robot.robotGetArtByPeriod(menuId,artId);//到自己的数据库匹配文章
						//发送文章消息
						return sendArt(articleList, respJson, fromUserName, toUserName);
					}else {
						return MessageResponse.getTextMessage(toUserName , fromUserName , keyWords.getValuess());
					}
				}else {
					return MessageResponse.getTextMessage(toUserName , fromUserName , "请求处理异常，请稍候尝试！");
				}

            }
        }else{
			//推送一条消息给管理员 提示没有设置公众号的默认回复内容
        }
        return MessageResponse.getTextMessage(fromUserName , toUserName , "");
    }

    /** 处理会话报名 */
    private static String signSession(String json,String contentStr,String fromUserName,String toUserName,String menuId) {
		String name = "";
		String phone = "";
		String address = "";
		if (!"".equals(json)) {
			List<WechatActivity> list = JSONUtils.toList(json, WechatActivity.class);
			if (list != null) {
				Iterator<WechatActivity> iterator = list.iterator();
				while (iterator.hasNext()) {
					WechatActivity wechatActivity = iterator.next();
					if (wechatActivity != null) {
						String id = wechatActivity.getId();
						String title = null;
						String descM = null;
						if (wechatActivity.getDescM() != null) {
							if (wechatActivity.getTitle() != null) {
								if (!"".equals(wechatActivity.getTitle())) {
									title = ToolsASCIIChang.asciiToString(wechatActivity.getTitle());
									wechatActivity.setTitle(title);
								}
							}
							if (!"".equals(wechatActivity.getDescM())) {
								descM = ToolsASCIIChang.asciiToString(wechatActivity.getDescM());
								wechatActivity.setDescM(descM);
								//TODO 匹配关键字
								if (contentStr.contains(descM)) {
									//匹配，但是输入长度就是匹配关键字长度
									if (contentStr.length() == descM.length()) {
										String returnStr = ToolsASCIIChang.asciiToString(wechatActivity.getReturnPage());
										return MessageResponse.getTextMessage(fromUserName, toUserName, returnStr);
									}
									//匹配，但是输入中匹配不到“+”
									else if (!contentStr.contains("+")) {
										String returnStr = "报名格式不正确，" + ToolsASCIIChang.asciiToString(wechatActivity.getReturnPage());
										return MessageResponse.getTextMessage(fromUserName, toUserName, returnStr);
									}
									//匹配
									else {
										String messageInfo = contentStr.substring(descM.length() + 1, contentStr.length());
										String[] messageInfoArray = messageInfo.split("\\u002B");
										if (messageInfoArray.length <= 1) {
											String returnStr = "报名格式不正确，" + ToolsASCIIChang.asciiToString(wechatActivity.getReturnPage());
											return MessageResponse.getTextMessage(fromUserName, toUserName, returnStr);
										}
										if (messageInfoArray.length >= 3) {
											String returnStr = "报名格式不正确，" + ToolsASCIIChang.asciiToString(wechatActivity.getReturnPage());
											return MessageResponse.getTextMessage(fromUserName, toUserName, returnStr);
										}
										if (messageInfoArray.length <= 2) {
											name = messageInfoArray[0].trim();
											phone = messageInfoArray[1].trim();
											if (name == null || "".equals(name)) {
												return MessageResponse.getTextMessage(fromUserName, toUserName, "报名失败，姓名不能为空!!");
											} else if (phone == null || "".equals(phone)) {
												return MessageResponse.getTextMessage(fromUserName, toUserName, "报名失败，电话号码不能为空!!");
											} else if (!ToolsString.isMobile(phone)) {
												return MessageResponse.getTextMessage(fromUserName, toUserName, "报名失败，电话号码格式不正确!!");
											}
											name = ToolsASCIIChang.stringToAscii(name);
											phone = ToolsASCIIChang.stringToAscii(phone);
										}
										if (messageInfoArray.length == 3) {
											name = messageInfoArray[0].trim();
											phone = messageInfoArray[1].trim();
											address = messageInfoArray[2].trim();
											if (name == null || "".equals(name)) {
												return MessageResponse.getTextMessage(fromUserName, toUserName, "报名失败，姓名不能为空!!");
											} else if (phone == null || "".equals(phone)) {
												return MessageResponse.getTextMessage(fromUserName, toUserName, "报名失败，电话号码不能为空!!");
											} else if (!ToolsString.isMobile(phone)) {
												return MessageResponse.getTextMessage(fromUserName, toUserName, "报名失败，电话号码格式不正确!!");
											} else if (address == null || "".equals(address)) {
												return MessageResponse.getTextMessage(fromUserName, toUserName, "报名失败，地址不能为空!!");
											}
											name = ToolsASCIIChang.stringToAscii(name);
											phone = ToolsASCIIChang.stringToAscii(phone);
											address = ToolsASCIIChang.stringToAscii(address);

										}

										String getJson = ToolsHttpRequest.sendGet(Set.SYSTEM_URL + "activityinterface/signSession", "menuId=" +
												menuId + "&openid=" + fromUserName + "&id=" + id + "&name=" + name + "&phone=" + phone + "&address=" + address);
										if (getJson != null) {
											if ("1".equals(getJson)) {
												return MessageResponse.getTextMessage(fromUserName, toUserName, "你已经成功报名" + title + "活动");
											} else if ("2".equals(getJson)) {
												return MessageResponse.getTextMessage(fromUserName, toUserName, "报名失败，不能重复报名");
											} else if ("3".equals(getJson)) {
												return MessageResponse.getTextMessage(fromUserName, toUserName, "你已经成功报名" + title + "活动*");
											} else {
												return MessageResponse.getTextMessage(fromUserName, toUserName, "报名失败，活动报名已经结束");
											}
										} else {
											return MessageResponse.getTextMessage(fromUserName, toUserName, "报名失败");
										}
									}

								}
							}
						}


					}
				}
			}else {
				return null;
			}
		}
		return null;
	}

}
