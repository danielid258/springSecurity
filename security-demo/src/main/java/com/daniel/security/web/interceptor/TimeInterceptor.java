package com.daniel.security.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * on 5/25/2018.
 *
 * 拦截器
 */
@Component
public class TimeInterceptor implements HandlerInterceptor{

    /**
     * controller中的目标方法被调用前执行
     *
     * @param request
     * @param response
     * @param handler 目标controller
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("TimeInterceptor preHandle execute ...");

        long startTime = new Date().getTime();
        request.setAttribute("startTime", startTime);

        System.out.println("TimeInterceptor preHandle ==> target controller class :" + ((HandlerMethod) handler).getBean().getClass().getName());
        System.out.println("TimeInterceptor preHandle ==> target controller method :" + ((HandlerMethod) handler).getMethod().getName());
        return true;
    }

    /**
     * controller中的目标方法 调用完成后并且未抛出exception 时执行
     *
     * @param request
     * @param response
     * @param handler 目标controller
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("TimeInterceptor postHandle execute ...");

        Long startTime = (Long) request.getAttribute("startTime");
        System.out.println("TimeInterceptor postHandle used time :" + (new Date().getTime() - startTime));
    }

    /**
     * controller中的目标方法 调用完成后执行 无论目标方法是否异常都执行
     *
     * @param request
     * @param response
     * @param handler 目标controller
     * @param e 目标方法中抛出的异常 如果ControllerExceptionHandler中已经捕获并处理了异常 此处将获取不到异常信息
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        System.out.println("TimeInterceptor afterCompletion execute ...");

        Long startTime = (Long) request.getAttribute("startTime");
        System.out.println("TimeInterceptor afterCompletion used time :" + (new Date().getTime() - startTime));

        if (e != null)
            System.out.println("exception is :" + e.getMessage());
    }
}
