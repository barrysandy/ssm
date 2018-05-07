package com.xiaoshu.tools.single;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例MapPublicNumber
 */
public class MapPublicNumber {

	private static MapPublicNumber instance = null;
    private static Map<String, String> map = null;
    private MapPublicNumber(){}
      
    public static MapPublicNumber getInstance() {
        if(instance == null){//懒汉式  
            instance = new MapPublicNumber();
            map = new HashMap<String,String>();
        }
        return instance;  
    }

    /**
     * 获取map
     * @return
     */
    public Map<String, String> getMap() {
        return map;
    }
}
