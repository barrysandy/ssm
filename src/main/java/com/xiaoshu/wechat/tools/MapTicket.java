package com.xiaoshu.wechat.tools;

import java.util.HashMap;
import java.util.Map;

import com.xiaoshu.wechat.pojo.TicketObject;



/**
 * 单例模式设计    
 * 保存
 * 微信页面签名、调用JS的参数
 * @author XGB
 *
 */
public class MapTicket {
	private Map<String,TicketObject> map = new HashMap<String,TicketObject>();

	
	public Map<String, TicketObject> getMap() {
		return map;
	}

	public void setMap(Map<String, TicketObject> map) {
		this.map = map;
	}

	private MapTicket() {}  
    private static MapTicket ticket=null;  
    
    //静态工厂方法   
	public static synchronized MapTicket getInstance() {  
        if (ticket == null) {    
        	ticket = new MapTicket();  
        }    
       return ticket;  
	}
	
}
