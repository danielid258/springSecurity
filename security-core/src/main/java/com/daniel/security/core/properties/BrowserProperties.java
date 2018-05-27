package com.daniel.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Daniel on 2018/5/27.
 */
@ConfigurationProperties(prefix = "daniel.security")
public class BrowserProperties {

    /**
     * 默认的登录页面
     *
     * 如果客户端配置了自己的登录页面 认证时跳到客户端自定义的认证页面
     *
     * 否则调到默认的登录页面 /sign.html
     */
    private String loginPage="/sign.html";

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
