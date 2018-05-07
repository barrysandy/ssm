package com.xiaoshu.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Kun on 2017/12/21 0021.
 */
public class ParamUtil {


    /**
     * 私有构造方法，防止类的实例化，因为工具类不需要实例化。
     */
    private ParamUtil() {
    }

    /**
     * 取得并返回request参数值
     * @param request request
     * @param paramName 参数名
     * @return String
     * @author kun
     */
    public static String getString(HttpServletRequest request, String paramName) {
        String temp = request.getParameter(paramName);
        String temp1 = (String) request.getAttribute(paramName);
        if (temp != null && !"".equals(temp)) {
            return formatHttpString(temp);
        } else if (temp1 != null && !"".equals(temp1)) {
            return temp1;
        } else {
            return null;
        }
    }

    /**
     * 取得并返回request参数值,如果返回空值则用defaultString代替
     * @param request
     * @param paramName 参数名
     * @param defaultString 默认值
     * @return STRING
     * @author kun
     */
    public static String getString(HttpServletRequest request, String paramName, String defaultString) {
        String temp = getString(request, paramName);
        return temp == null ? defaultString : temp;
    }

    /**
     * 取得并返回request参数值,已处理了中文
     * @param request
     * @param paramName 参数
     * @author kun
     * @return String
     */
    public static String getFormatString(HttpServletRequest request, String paramName) {
        String temp = StringUtils.toUTF8(getString(request, paramName));
        return temp == null ? null : temp;
    }

    /**
     * 取得并返回request参数值,已处理了中文，如果返回空值则用defaultString代替
     * @param request
     * @param paramName 参数
     * @author kun
     * @return String
     */
    public static String getFormatString(HttpServletRequest request, String paramName, String defaultString) {
        String temp = StringUtils.toUTF8(getString(request, paramName));
        return temp == null ? defaultString : temp;
    }

    /**
     * 取得并返回request参数值,已转换为整型数，如果返回空值则用defaultInt代替
     * @param request
     * @param paramName 参数
     * @author kun
     * @return int
     */
    public static int getInt(HttpServletRequest request, String paramName, int defaultInt) {
        try {
            String temp = getString(request, paramName);
            return temp == null ? defaultInt : Integer.parseInt(temp.trim());
        } catch (Exception e) {
            /*遇到异常直接返回默认值*/
            e.printStackTrace();
            return defaultInt;
        }
    }

    /**
     * 取得并返回request参数值,已转换为浮点数，如果返回空值则用defaultInt代替
     * @param request
     * @param paramName 参数
     * @return float
     * @author kun
     */
    public static float getFloat(HttpServletRequest request, String paramName, int defaultInt) {
        try {
            String temp = getString(request, paramName);
            return temp == null ? defaultInt : Float.parseFloat(temp);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultInt;
        }
    }

    /**
     * 取得并返回request参数值,已转换为浮点数，如果返回空值则用defaultInt代替
     * @param request
     * @param paramName 参数
     * @return double
     * @author kun
     */
    public static double getDouble(HttpServletRequest request, String paramName, double defaultDouble) {
        try {
            String temp = getString(request, paramName);
            return temp == null ? defaultDouble : Double.parseDouble(temp);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultDouble;
        }
    }

    /**
     * 取得并返回request参数值,已转换为日期类型
     * @param request
     * @param paramName 参数
     * @return Date
     */
    public static Date getDate(HttpServletRequest request, String paramName) {
        String temp = getString(request, paramName);
        if (temp == null || temp.trim().length() == 0){
            return new Date();
        }
        else{
            return DateUtils.changeStrToDate(temp);
        }
    }

    /**
     * 格式化输入内容，防止sql注入
     * @param str 参数字符串
     * @return String
     * @author kun
     */
    private static String formatHttpString(String str) {
        if (str == null){
            return "";
        }
        /*去掉and*/
        str = str.replaceAll(" and ", "");
        /*去掉or*/
        str = str.replaceAll(" or ", "");
        //str = str.replaceAll("%20", ""); //去空格
        // str = str.replaceAll(" ", "");   //去空格
        return str;
    }

}
