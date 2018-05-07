package com.xiaoshu.tools.single;


/**
 * 单例SignJSSDK
 */
public class SignJSSDK {

	private static SignJSSDK instance = null;
    private SignJSSDK(){}
      
    public static SignJSSDK getInstance() {
        if(instance == null){//懒汉式  
            instance = new SignJSSDK();
        }
        return instance;  
    }
}
