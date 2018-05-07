package com.xiaoshu.tools.single;


import com.xiaoshu.entity.MessageRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单例 SignMessageMQ
 * 短信的队列存储
 */
public class SignMessageMQ {

	private static SignMessageMQ instance = null;
    private static Map<String, List<MessageRecord>> map = null; //存放临时集合
    private SignMessageMQ(){}
      
    public static SignMessageMQ getInstance() {
        if(instance == null){//懒汉式  
            instance = new SignMessageMQ();
            map = new HashMap<String, List<MessageRecord>>();
            map.put("sendMsg",new ArrayList<MessageRecord>());//
        }
        return instance;  
    }

    /**
     * 获取map
     * @return
     */
    public Map<String, List<MessageRecord>> getMap() {
        return map;
    }
}
