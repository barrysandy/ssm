package com.xiaoshu.po;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例UserClickGroupMap
 */
public class UserClickGroupMap {

	private static UserClickGroupMap instance = null;
    private static Map<String, UserClickGroup> map = null;
    private UserClickGroupMap(){}
      
    public static UserClickGroupMap getInstance() {
        if(instance == null){//懒汉式  
            instance = new UserClickGroupMap();
            map = new HashMap<String,UserClickGroup>();
        }
        return instance;  
    }

    /**
     * 获取map
     * @return
     */
    public Map<String, UserClickGroup> getMap() {
        return map;
    }
}
