package com.xiaoshu.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;  
import org.aspectj.lang.annotation.Before;  
import org.aspectj.lang.annotation.Pointcut;  
  
/** 
 * 切面 
 */
@Aspect  
public class MyInterceptor {
    @Pointcut("execution(* com.xiaoshu.service.*ServiceImpl.*(..))")
//    @Pointcut("execution(* com.xiaoshu.controller.admin.*.*Controller.*(..))")
    private void anyMethod(){}//定义一个切入点  
      
    @Before("anyMethod() && args(name)")  
    public void doAccessCheck(String name){  
        System.out.println("*****前置通知 name: " + name + "******" );
    }  
      
    @AfterReturning("anyMethod()")
    public void doAfter(){
        System.out.println("后置通知");
    }
      
    @After("anyMethod()")
    public void after(){
        System.out.println("最终通知");
    }
      
    @AfterThrowing("anyMethod()")
    public void doAfterThrow(){
        System.out.println("例外通知");
    }

    @Around("anyMethod()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("进入环绕通知");
        Object object = pjp.proceed();//执行该方法
        System.out.println("退出方法");
        return object;
    }
}  