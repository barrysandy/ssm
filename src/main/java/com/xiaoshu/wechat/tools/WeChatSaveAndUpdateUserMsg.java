package com.xiaoshu.wechat.tools;


import com.xiaoshu.api.Api;
import com.xiaoshu.tools.JSONUtils;
import com.xiaoshu.tools.ToolsHttpRequest;
import com.xiaoshu.vo.JsonData;
import org.slf4j.Logger;

/**
 * 微信用户处理（保存/更新）工具类
 *  @author XGB
 */  
public class WeChatSaveAndUpdateUserMsg {

	/**
	 * 对用户进行处理（保存/更新）
	 * @param fromUserName 微信用户的openid
	 * @param fromUserName 微信用户的openid
	 * @author XGB
	 * @date 2018-01-19 11:07
	 */
	public static void handleUser(String fromUserName,String menuId){
		//查找用户在不在数据库，如果查不到该微信用户则添加至数据库
		String json = ToolsHttpRequest.sendGet(Api.GET_USEREXIT, "openid="+fromUserName+"&menuId="+menuId);
		System.out.println("====check===user fromUserName=" + fromUserName + " josn :" + json);
		if(json != null){
			if(!"".equals(json)){
				JsonData jsonData = JsonData.getObject(json);
				//TODO 根据返回的数据进行判断是否跳过或者更新当前用户
				if(jsonData != null){
					if("ok".equals(jsonData.getStatus())){
						if("2".equals(jsonData.getData())){
							//需要更新
							Update(fromUserName,menuId);
						}
						if("1".equals(jsonData.getData())){
							//需要更新
							if("update".equals(jsonData.getDescM())){
								Update(fromUserName,menuId);
							}
						}
						//更新关注状态
						ToolsHttpRequest.sendGet(Api.UPDATE_USER_SUBSCRIBE, "openid="+fromUserName+"&menuId="+menuId +"&subscribe=1");

					}
				}
			}
		}
	}

	/** 更新用户信息 */
	private static void Update(String fromUserName,String menuId){
		//判断用户信息完整度/并更新用户信息
		String json = ToolsHttpRequest.sendGet(Api.UPDATE_USER, "openid="+fromUserName+"&menuId="+menuId);
		System.out.println("UPDATE_USER JSON : " + json);
	}
}  

