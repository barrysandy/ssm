package com.xiaoshu.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class ToolsDate {
	
	/** 完整时间 yyyy-MM-dd HH:mm:ss (精确到毫秒)*/
	public static final String simpleSecond = "yyyy-MM-dd HH:mm:ss";
	
	/** 完整时间 yyyy-MM-dd HH:mm  (精确到分)*/
	public static final String simpleMinute = "yyyy-MM-dd HH:mm";
	
	/** 完整时间 yyyy-MM-dd  (精确到天)*/
    public static final String simpleDay = "yyyy-MM-dd";
	
	
    /** 年月日时分秒(无下划线) yyyyMMddHHmmss (精确到毫秒)*/
    public static final String dtLongSecond = "yyyyMMddHHmmss";
    
    /** 年月日时分(无下划线) yyyyMMddHHmm (精确到分)*/
    public static final String dtLongMinute = "yyyyMMddHHmmss";
    
    /** 年月日时(无下划线) yyyyMMddHHmm (精确到小时)*/
    public static final String dtHour = "yyyyMMddHH";
    
    /** 年月日(无下划线) yyyyMMdd (精确到天)*/
    public static final String dtDay = "yyyyMMdd";


	/**
	 * 获取当前时间
	 * @param format
	 * @return 返回String类型时间字符串
	 */
	public static String getStringDateToFormat(Date date ,String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);//HH:mm:ss
		String dateString = formatter.format(date);
		return dateString;
	}


	/**
	 * 获取当前时间
	 * @param format
	 * @return 返回String类型时间字符串
	 */
	public static String getStringDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);//HH:mm:ss
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	
	/**
	 * 获取当前上一个月时间
	 * @param amount -1 表示上一个月
	 * @return 返回String类型时间字符串格式yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDateLastMonth(int amount) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        //过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, amount);
        Date currentTime = c.getTime();
        String dateString = format.format(currentTime);
		return dateString;
	}
	
	
	/**
	 * 获取当天0点时间 
	 * @param format 返回时间格式
	 * @return 返回String类型时间字符串格式
	 */
	public static String getTimeZero(String format) {
		long current = new Date().getTime();//当前时间毫秒数  
	    long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数  
	    
		Date currentTime = new Date(zero);//获得当天0点的时间
		SimpleDateFormat formatter = new SimpleDateFormat(format);//HH:mm:ss
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	

	/**
	 * 获取输入的年时间
	 * @param year 年  0表示1900年
	 * @return 返回String类型时间字符串格式yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDateSetYear(int year) {
		SimpleDateFormat formatter = new SimpleDateFormat(simpleSecond);//HH:mm:ss
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		currentTime.setYear(year);
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	
	/**
	 * 获取当前系统时间 
	 * @return 返回Date类型
	 */
	public static Date getDate(String format) {
		Date dt = new Date();   
		SimpleDateFormat sdf = new SimpleDateFormat(format);//HH:mm:ss
		sdf.format(dt);
		return dt;
	}


	/**
	 * 获取当前系统时间 
	 * @return 返回Date类型
	 */
	public static Date getDate(String format,String date) {
		Date currentTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);//HH:mm:ss
		try {
			currentTime = sdf.parse(date);
			sdf.format(currentTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentTime;
	}

	/**
	 * 获取当前系统时间
	 * @return 返回Date类型
	 */
	public static Calendar getCalendar(String format,String date) {
		Calendar currentTime = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);//HH:mm:ss
			Date dateStr =sdf.parse(date);
			currentTime = Calendar.getInstance();
			currentTime.setTime(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentTime;
	}


	
	/**
	 * 传入两个字符串日期，获取最大的日期
	 * @param date1 日期1
	 * @param date2 日期2
	 * @return 返回最大的日期字符串
	 */
	public static String getMaxTime(String format,String date1,String date2) {
		String bigDate = "";
        Date dt1 = getDate(format, date1);
        Date dt2 = getDate(format, date2);
        if (dt1.getTime() > dt2.getTime()) {
//           System.out.println("dt1大");
           bigDate = date1;
        } else if(dt1.getTime() < dt2.getTime()) {
//           System.out.println("dt2大");
           bigDate = date2;
        }
		return bigDate;
		
	}


	/**
	 * 用来比较传入的时间大小情况，date1小于日期date2，这是条件，比较date于两个时间的关系
	 * @param date1 日期1
	 * @param date2 日期2
	 * @return returnInt 0 表示date在两个时间之间 1 表示date在时间之前 2表示在时间之后 3表示异常,可能date1>date2
	 */
	public static int getTheRelationshipBetweenTwoTimeNodes(Date date,Date date1,Date date2) {
		int returnInt = 3;
		if (date.getTime() < date1.getTime()) {
        	returnInt = 1;
			return returnInt;
		}
		if (date.getTime() > date2.getTime()) {
			returnInt = 2;
			return returnInt;
		}
		if ((date.getTime() > date1.getTime()) && (date.getTime() < date2.getTime())) {
			returnInt = 0;
			return returnInt;
		}
		return returnInt;
	}



	/**
	 * 用来比较传入的时间大小情况，date1小于日期date2，这是条件，比较date于两个时间的关系
	 * @param dateStr1 日期1
	 * @param dateStr2 日期2
	 * @return returnInt 0 表示date在两个时间之间 1 表示date在时间之前 2表示在时间之后 3表示异常,可能date1>date2
	 */
	public static int getTheRelationshipBetweenTwoTimeNodesStringDate(Date date,String dateStr1,String dateStr2) {

		Date date1 = StringformatDate(ToolsDate.simpleSecond,dateStr1);
		Date date2 = StringformatDate(ToolsDate.simpleSecond,dateStr2);

//		System.out.println("======================================================");
//		System.out.println("date : " + ToolsDate.getStringDateToFormat(date,ToolsDate.simpleSecond));
//		System.out.println("date1 : " + ToolsDate.getStringDateToFormat(date1,ToolsDate.simpleSecond));
//		System.out.println("date2 : " + ToolsDate.getStringDateToFormat(date2,ToolsDate.simpleSecond));
//		System.out.println("======================================================");

		int returnInt = 3;
		if (date.getTime() < date1.getTime()) {
//			System.out.println("======================================================");
//			System.out.println("当前date 比date1 date2 小 ");
//			System.out.println("======================================================");
			returnInt = 1;
			return returnInt;
		}
		if (date.getTime() > date2.getTime()) {
			returnInt = 2;
//			System.out.println("======================================================");
//			System.out.println("当前date 比date1 date2 大 ");
//			System.out.println("======================================================");
			return returnInt;
		}
		if ((date.getTime() > date1.getTime()) && (date.getTime() < date2.getTime())) {
//			System.out.println("======================================================");
//			System.out.println("当前date 在date1 date2 之间 ");
//			System.out.println("======================================================");
			returnInt = 0;
			return returnInt;
		}
		return returnInt;
	}


	/**
	 * 用来比较传入的时间大小情况，date1小于日期date2，这是条件，比较date于两个时间的关系
	 * @param dateStr 倒计时日期
	 * @return returnInt 0 表示date在两个时间之间 1 表示date在时间之前 2表示在时间之后 3表示异常,可能date1>date2
	 */
	public static Integer getCountDown(String dateStr) {
		Date date1 = StringformatDate(ToolsDate.simpleSecond,dateStr);
		Date date = new Date();
		int countDown = (int)(date1.getTime() - date.getTime()) / 1000;
		return countDown;
	}



	/**
	 * 将Long格式的时间戳（毫秒）转为format格式的字符串时间输出
	 * @param format 转换格式，参考本类的属性
	 * @param longDate 毫秒的时间戳
	 * @return 转换后的字符格式时间
	 */
	public static String longTimeFormatStringTime(String format,Long longDate){
		Date date = new Date(longDate);
		SimpleDateFormat formatter = new SimpleDateFormat(format);//HH:mm:ss
		return formatter.format(date);
	}


	/**
	 * 字符串时间转为Date
	 * @param format 转化格式
	 * @param dateStr
	 * @return
	 */
	public static Date StringformatDate(String format,String dateStr){
		dateStr = formatStringDate(format , dateStr );
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		}catch (Exception e){
			e.printStackTrace();
		}
		return date;
	}

	public static String formatStringDate(String format,String dateStr){
		if(dateStr != null){
			String[] times = dateStr.split(" ");
			String timeAf = times[0];
			String timeAf2 = times[1];
			String[] timesAfArray = timeAf.split("-");
			StringBuffer sb = new StringBuffer();
			sb.append(timesAfArray[0]);
			sb.append("-");
			if(timesAfArray[1].length() == 1){
				sb.append("0");
			}
			sb.append(timesAfArray[1]);
			sb.append("-");
			if(timesAfArray[2].length() == 1){
				sb.append("0");
			}
			sb.append(timesAfArray[2]);
			sb.append(" ");
			sb.append(timeAf2);
			sb.append(":00");
			dateStr = sb.toString();
		}
		return dateStr;
	}


	public static String getTime(String time){
		if(time != null){
			StringBuffer sb = new StringBuffer();
			sb.append(time.substring(0, 4));
			sb.append("年");
			sb.append(time.substring(5, 7));
			sb.append("月");
			sb.append(time.substring(8, 10));
			sb.append("日");
			return sb.toString();
		}
		return null;
	}

}
