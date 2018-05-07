package com.xiaoshu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kun on 2017/04/25 0025.
 */
public class DateUtils {

    public static final String CHINESE_DATE_FORAMT_YMD = "yyyy年MM月dd日";
    public static final String CHINAESE_DATETIME_FORMAT_YMDHMS = "yyyy年MM月dd日 HH:mm:ss";
    public static final String CHINAESE_DATETIME_FORMAT_YMDHM = "yyyy年MM月dd日HH时mm分";
    public static final String CHINAESE_WEEK_FORMAT_YW = "xxxx年xx周";
    public static final String CHINAESE_WEEK_FORMAT = "xxxxxx";
    public static final String DEFAULT_DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_FORMAT_YM = "yyyy-MM";
    public static final String DEFAULT_DATE_FORMAT_MD = "MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT_YMDH = "yyyy-MM-dd HH";
    public static final String DEFAULT_DATETIME_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATETIME_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_DATETIME_FORMAT_MDH = "MM-dd HH";
    public static final String NOSYMBOL_DATETIME_FORMAT_YMDHMS = "yyyyMMddHHmmss";
    public static final String NOSYMBOL_DATETIME_FORMAT_YMDH = "yyyyMMddHH";
    public static final String NOSYMBOL_DATETIME_FORMAT_YMD = "yyyyMMdd";
    public static final String NOSYMBOL_TIME_FORMAT_HMS = "HH:mm:ss";
    public static final String SPRIT_DATETIME_FORMAT_YMDHMS = "yyyy/MM/dd HH:mm:ss";
    public static final String SPRIT_DATETIME_FORMAT_YMDHMSS = "yyyy/MM/dd HH:mm:ss.S";
    public static final String SPRIT_DATETIME_FORMAT_YMDHM = "yyyy/MM/dd HH:mm";
    public static final String SPRIT_DATETIME_FORMAT_YMD = "yyyy/MM/dd";
    public static final String SPRIT_DATETIME_FORMAT_YMDH = "YYYY/MM/dd HH";
    public static final String FLASH_DATETIME_FORMAT_YMDH = "yyyy,MM,dd,HH,mm,ss";
    public static final String DATE_FORMAT_HOUR = "HH:00";

    /**
     * 根据传入的日期转换成字符形式的日期
     * @param date
     * @return 如：2005-12-25 08:25:36
     */
    public static String changeDateToStr(Date date,String format) {
        if (date==null){
            return "";
        }
        if (StringUtils.isBlank(format)){
            format = "yyyy-MM-dd HH:mm:ss";//为空就默认以这个格式进行转换
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format); //yyyy-MM-dd HH:mm:ss 等等格式
        String NDate = formatter.format(date);
        return NDate;
    }
    /**
     * 通过出生日期,获得年龄
     * @Param birthday
     * @return age
     */
    public static int getAge(Date birthDay){

        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("出生日期不能在未来!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }
    /**
     * 把字符串转换成时间，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     */
    public static Date changeStrToDate(String dateStr) {
        Date temp1 = null;
        if (dateStr == null){
            return null;
        }
        if (dateStr.equals("")){
            return null;
        }
        SimpleDateFormat formatter = null;
        try {
            if (dateStr.indexOf(" ") != -1) {
                String[] aa = StringUtils.split(dateStr, ":");
                if (aa.length == 3)
                    formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                else if (aa.length == 2)
                    formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                else
                    formatter = new SimpleDateFormat("yyyy-MM-dd HH");
            } else {
                formatter = new SimpleDateFormat("yyyy-MM-dd");
            }
            temp1 = formatter.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp1;
    }
}
