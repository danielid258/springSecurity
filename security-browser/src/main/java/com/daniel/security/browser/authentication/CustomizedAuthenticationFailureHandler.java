package com.daniel.security.browser.authentication;

import com.daniel.security.browser.support.SimpleResponse;
import com.daniel.security.core.properties.LoginType;
import com.daniel.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Daniel on 2018/5/27.
 *
 * 自定义登录失败处理器
 */
@Component
//public class CustomizedAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomizedAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    Logger logger = LoggerFactory.getLogger(CustomizedAuthenticationFailureHandler.class);

    /**
     * 将Authentication对象转为json 由spring注入
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.info("login failure");
        //客户端指定返回格式 JSON
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
        } else {
            //客户端未指定返回格式 默认跳转
            super.onAuthenticationFailure(request,response,e);
        }

    }
}
