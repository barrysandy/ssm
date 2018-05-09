package com.xiaoshu.api;

/**
 * 系统设置类
 * @author
 */
public class Set {

    /**
     * 项目地址，用于接口前置地址，完整地址需要加上controller
     * http://w17b197823.iok.la/
     * http://www.zhishengxixing.com/ssm/
     * http://www.daxi51.com/ssm/
     * http://118.24.118.23/ssm/
     */
    public static final String SYSTEM_URL = "http://w17b197823.iok.la/ssm/";


    /**
     * 文件系统项目地址
     * http://w17b197823.iok.la/
     * http://www.zhishengxixing.com/ssm/
     * http://www.daxi51.com/ssm/
     * http://118.24.118.23/ssm/
     */
    public static final String SYSTEM_SSM_FILE = "http://www.daxi51.com/ssm_file/";

    /** LoginInterceptor 拦截器不拦截的地址中包含的字符串 */
    public static final String INTERCEPTORURL = "interface";

    /** LoginInterceptor 拦截器不拦截的地址中包含的字符串(user用户session中不需要user登录就可以访问的路径需要加上的字符串) */
    public static final String INTERCEPTOR_NOTNEED_URL = "NoUser";

    /** LoginInterceptor user用户session中需要user登录 */
    public static final String INTERCEPTOR_USER_URL = "InUser";

}
