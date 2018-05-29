package com.daniel.security.browser.authentication;

import com.daniel.security.core.properties.LoginType;
import com.daniel.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Daniel on 2018/5/27.
 * <p>
 * 自定义的登录成功处理器
 */
@Component
//public class CustomizedAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
public class CustomizedAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    Logger logger = LoggerFactory.getLogger(CustomizedAuthenticationSuccessHandler.class);

    /**
     * 将Authentication对象转为json 由spring注入
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

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

        //客户端指定返回格式 JSON
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        } else {
            //客户端未指定返回格式 默认跳转
            super.onAuthenticationSuccess(request,response,authentication);
        }

    }
}
