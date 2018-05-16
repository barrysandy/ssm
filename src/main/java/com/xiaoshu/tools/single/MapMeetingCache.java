package com.xiaoshu.tools.single;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例 MapMeetingCache
 */
public class MapMeetingCache {

	private static MapMeetingCache instance = null;
    private static Map<String, String> map = null;
    private MapMeetingCache(){}
      
    public static MapMeetingCache getInstance() {
        if(instance == null){//懒汉式  
            instance = new MapMeetingCache();
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
