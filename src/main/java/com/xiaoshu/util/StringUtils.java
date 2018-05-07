package com.xiaoshu.util;

import java.util.*;

/**
 * Created by Kun on 2017/04/25 0025.
 */
public class StringUtils {

    public static String[] split(String source, String delim) {
        String[] wordLists;
        if (source == null) {
            wordLists = new String[1];
            wordLists[0] = source;
            return wordLists;
        }
        if (delim == null) {
            delim = ",";
        }
        StringTokenizer st = new StringTokenizer(source, delim);
        int total = st.countTokens();
        wordLists = new String[total];
        for (int i = 0; i < total; i++) {
            wordLists[i] = st.nextToken();
        }
        return wordLists;
    }


    public static boolean isBlank(String str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }


    /**
     * 转utf8格式
     * @param str 参数
     * @return String
     * @author zhou.zhengkun
     * @date 2017/12/21 0021 16:07
     */
    public static String toUTF8(String str) {
        try {
            if (str == null){
                return null;
            }
            str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
            return str;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * String[]转int[]
     * @param arrs
     * @return
     */
    public static Integer[] stringArrToIntergerArr(String[] arrs){
        Integer[] ints = new Integer[arrs.length];
        for(int i=0;i<arrs.length;i++){
            ints[i] = Integer.parseInt(arrs[i]);
        }
        return ints;
    }

    /**
     * String[]转Set<Integer> 并去除重复元素
     * Integer[] 可有Set<Integer>.toArray()取得
     * @param arrs
     * @return
     */
    public static Set<Integer> stringArrToIntergerSet(String[] arrs){
        Set<Integer> integerSet = new HashSet<Integer>();
        for(int i=0;i<arrs.length;i++){
            integerSet.add(Integer.parseInt(arrs[i]));
        }
        return integerSet;
    }


    /**
     * 判断字符串是空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if("".equals(str)|| str==null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断字符串不是空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        if(!"".equals(str)&&str!=null){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 判断某一个字符串数组中是否含有某一字符串
     * @param str
     * @param strArr
     * @return
     */
    public static boolean existStrArr(String str,String []strArr){
        return Arrays.asList(strArr).contains(str);
    }


    public static boolean isEquals(String str1, String str2) {
        if (str1 == null || str2 == null){
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }

}
