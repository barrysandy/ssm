package com.xiaoshu.tools.single;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例 MapUserPhoneCode
 */
public class MapUserPhoneCode {

	private static MapUserPhoneCode instance = null;
    private static Map<String, String> map = null;
    private MapUserPhoneCode(){}
      
    public static MapUserPhoneCode getInstance() {
        if(instance == null){//懒汉式  
            instance = new MapUserPhoneCode();
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
