package com.daniel.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Daniel on 2018/5/27.
 *
 * 自定义的登录成功处理器
 */
@Component
public class CustomizedAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    Logger logger = LoggerFactory.getLogger(CustomizedAuthenticationSuccessHandler.class);

    /**
     * 将Authentication对象转为json 由spring注入
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 自定义登录成功处理逻辑
     *
     * 此处将用户的权限以格式返回给客户端
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("login successful");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(authentication));
    }
}
