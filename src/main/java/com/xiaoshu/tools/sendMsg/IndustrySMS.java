package com.xiaoshu.tools.sendMsg;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.xiaoshu.tools.ToolsHttpRequest;
import org.apache.commons.lang.StringUtils;

/**
 * 短信通知接口类
 * @Description:
 * @author John 
 * @date： 2018年3月7日 下午2:17:02
 */
public class IndustrySMS {

	private static String corpID = Config.CorpID;
	private static String pwd = Config.Pwd;
	private static String mobile = "";
	private static String content = "";

	/**
	 * 测试发送
	 * @author John 
	 * @date： 2018年3月7日 下午5:36:27
	 */
//	@Test
//	public void test() {
//		HashMap<String,Object> map=	link("17629261082", Template.UPDATE_PWD_CODE, "", new String[]{"387838","10"});
//		System.out.println(map.get("status").toString());
//	}

	/**
	 * 短信发送
	 * 
	 * @param mobile 发送目标手机 yzm 验证码 type 类型 1、验证码发送
	 * @param time 发送时间比如：20060912152435代表2006年9月12日15时24分35
	 * @return 发送结果
	 * @author John
	 * @throws Exception
	 */
	public static HashMap<String, Object> link(String mobile, String template, String time,String[] param) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(mobile)) {
			map.put("status", "0");
			map.put("msg", "手机号码为空或无效");
			return map;
		}
		String content = template;
		if(content.indexOf("+")!=-1){
			if(param != null && param.length>0){
				content = fetchContent(template,param);
				if(StringUtils.isBlank(content)){
					map.put("status", "0");
					map.put("msg", "参数错误");
					return map;
				}
			}else{
				map.put("status", "0");
				map.put("msg", "参数不能为空");
				return map;
			}
		}
		try {
			content = URLEncoder.encode(content.replaceAll("<br/>", " "), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} // 发送内容
		String body = "CorpID=" + corpID + "&Pwd=" + pwd + "&Mobile=" + mobile + "&Content=" + content
				+ "&Cell=&SendTime=" + time;
		String resultInt = ToolsHttpRequest.sendPost(Config.BASE_URL, body);
		Integer result = Integer.parseInt(resultInt);
		System.out.println("发送的结果码 result =" + result);
		System.out.println(content);
		String resultStr = parseResult(result);
		if (result > 0) {
			map.put("status", "1");
		} else {
			map.put("status", "0");
		}
		map.put("msg", resultStr);
		return map;
	}
	/**
	 * 装配发送内容
	 * @param template
	 * @param param
	 * @return
	 * @author John
	 * @date： 2018年3月8日 下午2:11:26
	 */
	private static String fetchContent(String template, String[] param) {
		String templateCopy = template;
		int copyLength =templateCopy.replaceAll("\\+","").length();
		int templateLength = template.length();
		int code = templateLength-copyLength;
		if(code != param.length){
			return "";
		}
		String content = template;
		for (int i = 0; i < param.length; i++) {
			content= content.replaceFirst("\\+", param[i]);
			
		}
		return content.toString();
	}

	/**
	 * 解析发送结果
	 * 
	 * @param result
	 * @return
	 * @author John @date： 2018年3月7日 下午3:23:00
	 */

	private static String parseResult(int result) {
		if (result >= 0) {
			return "发送成功！！";
		} else if (result == -1) {
			return "帐号未注册！";
		} else if (result == -2) {
			return "其他错误！";
		} else if (result == -3) {
			return "帐号或密码错误！";
		} else if (result == -5) {
			return "企业号帐户余额不足，请先充值再提交短信息！";
		} else if (result == -6) {
			return "定时发送时间不是有效时间格式！";
		} else if (result == -7) {
			return "提交信息末尾未加签名，请添加中文的企业签名【 】！";
		} else if (result == -8) {
			return "发送内容需在1到300个字之间";
		} else if (result == -9) {
			return "发送号码为空";
		} else if (result == -10) {
			return "定时时间不能小于当前系统时间！";
		} else if (result == -101) {
			return "调用接口速度太快";
		}
		return "未知错误";
	}

}
