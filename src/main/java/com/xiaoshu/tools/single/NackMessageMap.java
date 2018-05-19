package com.xiaoshu.tools.single;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例 NackMessageMap
 */
public class NackMessageMap {

	private static NackMessageMap instance = null;
    private static Map<String, String> map = null;
    private NackMessageMap(){}
      
    public static NackMessageMap getInstance() {
        if(instance == null){//懒汉式  
            instance = new NackMessageMap();
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
