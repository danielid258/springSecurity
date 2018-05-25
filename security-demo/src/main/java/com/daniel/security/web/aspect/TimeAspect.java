package com.daniel.security.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import java.util.Date;

/**
 * on 5/25/2018.
 */
//@Aspect
//@Component
public class TimeAspect {
    /**
     * 指定哪些方法会被切入
     *
     * @param pjp 当前被拦截到的类及方法和参数等信息
     * @return
     */
    @Around("execution(* com.daniel.security.web.controller.*.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("TimeAspect handleControllerMethod start ... ");
        long time = new Date().getTime();

        //获取目标方法参数信息
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println("TimeAspect handleControllerMethod ==> arg is :" + arg);
        }

        //调用目标方法 类似Filter的 chain.doFilter(request, response) 返回值object即为目标方法的返回值
        Object o = pjp.proceed();

        System.out.println("TimeAspect handleControllerMethod ==> target method return is :" + o);
        System.out.println("TimeAspect handleControllerMethod ==> used " + (new Date().getTime() - time));
        System.out.println("TimeAspect handleControllerMethod finish ... ");

        return o;
    }
}
