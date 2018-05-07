package com.xiaoshu.wechat.tools;

import com.xiaoshu.tools.ToolsASCIIChang;

/**
 * 抓取关键字匹配的类
 * @author XGB
 *
 */
public class GetKey {
	/**
	 * 从key中匹配关键字
	 * @param key 传入的字符串
	 * @return returnStr 为返回的String[] 数组的第一个值为类型，第二个值为抓取的关键字
	 * interfaceWeather 表示天气 
	 * key 表示要查询数据库的关键字
	 */
	public static String[] getStr(String key){
		key = ToolsASCIIChang.asciiToString(key);
		String[] returnStr = {"key",key};
		/**
		 * 特殊接口回复类
		 */
		if(key.indexOf("天气")!= -1){
			returnStr[0] = "interfaceWeather";
			returnStr[1] = "天气";
			return returnStr;
		}
		/**
		 * 文章类
		 */
		if(key.indexOf("文章")!= -1){
			returnStr[0] = "key";
			returnStr[1] = "文章";
			return returnStr;
		}
		if(key.indexOf("美文")!= -1){
			returnStr[0] = "key";
			returnStr[1] = "美文";
			return returnStr;
		}
		if(key.indexOf("公告")!= -1){
			returnStr[0] = "key";
			returnStr[1] = "公告";
			return returnStr;
		}
		/**
		 * 关键字回复类
		 */
		if(key.indexOf("key")!= -1){
			returnStr[0] = "key";
			returnStr[1] = "key";
			return returnStr;
		}
		/**
		 * 音乐类
		 */
		if(key.indexOf("音乐")!= -1){
			returnStr[0] = "key";
			returnStr[1] = "音乐";
			return returnStr;
		}
		/**
		 * 视频类
		 */
		if(key.indexOf("视频")!= -1){
			returnStr[0] = "key";
			returnStr[1] = "视频";
			return returnStr;
		}
		/**
		 * 特殊业务类
		 */
		if(key.indexOf("投票")!= -1){
			returnStr[0] = "service";
			returnStr[1] = key;
			return returnStr;
		}
		return returnStr;
	}
}
