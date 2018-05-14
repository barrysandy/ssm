package com.xiaoshu.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xiaoshu.entity.Log;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.LogService;
import com.xiaoshu.tools.ToolsGetIPAddress;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class ArchivesLogAspect {
    @Autowired private LogService logService;

    private final String CURRENT_LOGIN_AMIN = "currentUser";
    private final Logger logger = Logger.getLogger(this.getClass());

    private String requestPath = null ; // 请求地址  
    private String userName = "" ; // 用户名  
    private Map<?,?> inputParamMap = null ; // 传入参数  
    private Map<String, Object> outputParamMap = null; // 存放输出结果  
    private long startTimeMillis = 0; // 开始时间  
    private long endTimeMillis = 0; // 结束时间  
    private User user = null;
    private HttpServletRequest request = null;

    /** 
     *  
     * @Description: 方法调用前触发   记录开始时间  
     * @author fei.lei  
     * @date 2016年11月23日 下午5:10 
     * @param joinPoint 
     */ 
    public void before(JoinPoint joinPoint){  
        //System.out.println("被拦截方法调用之后调用此方法，输出此语句");  
        request = getHttpServletRequest();  
        //fileName  为例子
//        Object obj =request.getParameter("fileName");
//        System.out.println("方法调用前: " + obj);
        user = (User)request.getSession().getAttribute(CURRENT_LOGIN_AMIN);
        startTimeMillis = System.currentTimeMillis(); //记录方法开始执行的时间  
    }  
    
    /** 
     *  
     * @Description: 方法调用后触发   记录结束时间  
     * @author fei.lei  
     * @date 2016年11月23日 下午5:10 
     * @param joinPoint 
     */
    public  void after(JoinPoint joinPoint) {
//        System.out.println("@AfterReturning：模拟日志记录功能...");
//        System.out.println("@AfterReturning：目标方法为：" +
//                joinPoint.getSignature().getDeclaringTypeName() +
//                "." + joinPoint.getSignature().getName());
//        System.out.println("@AfterReturning：参数为：" +
//                Arrays.toString(joinPoint.getArgs()));
//        System.out.println("@AfterReturning：被织入的目标对象为：" + joinPoint.getTarget());
        request = getHttpServletRequest();
        String ip = ToolsGetIPAddress.getIPAddr(request);

        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();  

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            String param = " param"+ i +  joinPoint.getArgs()[i];
            stringBuffer.append(param);
//            System.out.println(param);
        }
//        System.out.println("join 2 " + joinPoint.getSignature().getName());
//        Object[] arguments = joinPoint.getArgs();
//        Class targetClass = null;
//        try {
//            targetClass = Class.forName(targetName);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Method[] methods = targetClass.getMethods();
//        String operationName = "";
//        for (Method method : methods) {
//            if (method.getName().equals(methodName)) {
//                Class[] clazzs = method.getParameterTypes();
//                if (clazzs!=null&&clazzs.length == arguments.length&&method.getAnnotation(ArchivesLog.class)!=null) {
//                    operationName = method.getAnnotation(ArchivesLog.class).operationName();
//                    break;
//                }
//            }
//        }
        endTimeMillis = System.currentTimeMillis();
        //格式化开始时间
        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);
        //格式化结束时间
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTimeMillis);
        //System.out.println(" 操作人: "+ user.getUsername()+" 请求方法: " + targetName + "-" + methodName + " 参数：" + stringBuffer +" 操作开始时间: "+startTime +" 操作结束时间: "+endTime);
        Log log = new Log(null, user.getUsername(), new Date(), "", ip, targetName + "-" + methodName , " 参数：" + stringBuffer +" 操作开始时间: "+startTime +" 操作结束时间: "+endTime);
        try{
            logService.insertLog(log);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * @Description: 获取request  
     * @author fei.lei  
     * @date 2016年11月23日 下午5:10 
     * @param  
     * @return HttpServletRequest
     */
    public HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;  
        HttpServletRequest request = sra.getRequest();
        return request;
    }
  
    /**
     *
     * @Title：around
     * @Description: 环绕触发
     * @author fei.lei
     * @date 2016年11月23日 下午5:10
     * @param joinPoint
     * @return Object
     * @throws Throwable
     */
    /*public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        return null;
    }*/

    /** 
     *  
     * @Title：printOptLog 
     * @Description: 输出日志  
     * @author fei.lei 
     * @date 2016年11月23日 下午5:10
     */  
    /*private void printOptLog() {  
        Gson gson = new Gson(); // 需要用到google的gson解析包  
        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTimeMillis);  
        logger.info("user :" +user.getName()+ " start_time: " +  startTime +" end_time: "+endTime);  
    }  */
    
    
}