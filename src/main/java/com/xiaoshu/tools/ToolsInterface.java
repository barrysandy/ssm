package com.xiaoshu.tools;

import com.xiaoshu.api.Set;
import net.sf.json.JSONObject;

public class ToolsInterface {

	/**
	 * 内部调用接口获取设置Set关键字的值
	 * @param kw 设置Set的查询条件
	 * @return 
	 */
	public static String getSetInterface(String kw) {
		String jsonstr = ToolsHttpRequest.sendGet(Set.SYSTEM_URL +"/wechatinf/GET/setvalue", "kw="+kw);
		JSONObject josnObject = JSONObject.fromObject(jsonstr);
		jsonstr = (String) josnObject.get(JSONUtils.DATA);
		return ToolsString.getStrRemoveBracket(jsonstr);
	}

	/**
	 * 内部调用接口获取设置Set关键字的全部值
	 * @param kw 设置Set的查询条件
	 * @return 
	 */
	public static String getSetBeanInterface(String kw) {
		return ToolsString.getStrRemoveBracket(ToolsHttpRequest.sendGet(Set.SYSTEM_URL +"/wechatinf/GET/setbean", "kw="+kw));
	}
	
}
