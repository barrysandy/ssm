package com.xiaoshu.tools;

public class ToolsASCIIChang {
	/**
	 * 字符串转换为Ascii
	 * 
	 * @param value
	 * @return
	 */
	public static String stringToAscii(String value) {
		StringBuffer sbu = new StringBuffer();
		if(!"".equals(value)){//不为空
			char[] chars = value.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (i != chars.length - 1) {
					sbu.append((int) chars[i]).append(",");
				} else {
					sbu.append((int) chars[i]);
				}
			}
		}else{
			sbu.append("");
		}
		return sbu.toString();

	}

	/**
	 * Ascii转换为字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String asciiToString(String value) {
		StringBuffer sbu = new StringBuffer();
		if(!"".equals(value)){//不为空
			String[] chars = value.split(",");
			for (int i = 0; i < chars.length; i++) {
				sbu.append((char) Integer.parseInt(chars[i]));
			}
		}else{
			sbu.append("");
		}
		return sbu.toString();
	}
}
