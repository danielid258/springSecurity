package com.daniel.security.core.social.qq.connect;

import com.daniel.security.core.social.qq.api.QQ;
import com.daniel.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * on 6/4/2018.
 *
 * customized Provider extends AbstractOAuth2ServiceProvider
 *
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ>{
    /**
     * 步骤1中将用户导向到 认证服务器的地址
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 步骤4中 向认证服务器请求访问令牌的地址
     */
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    private String appId;

    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }
}
