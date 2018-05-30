package com.daniel.security.browser.controller;

import com.daniel.security.browser.support.SimpleResponse;
import com.daniel.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Daniel on 2018/5/27.
 */
@RestController
public class BrowserSecurityController {
    Logger logger = LoggerFactory.getLogger(BrowserSecurityController.class);

    //当请求需要认证时 在请求跳转到对应的认证地址[由loginPage()指定跳转地址]前 springSecurity会把当前请求缓存到HttpSessionRequestCache中
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    SecurityProperties securityProperties;

    /**
     * 处理所有需要认证的请求
     *
     * html认证页面请求 -> 返回客户端指定的页面(客户端未指定跳到默认页面)
     *
     * json数据型请求 -> 返回401(未授权) 让客户端自行引导用户进行认证
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/authenticate/require")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html"))
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
        }
        return new SimpleResponse("the url need to handler,please go and sigin!");

    }
}
