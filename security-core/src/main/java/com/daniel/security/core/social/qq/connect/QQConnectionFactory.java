package com.daniel.security.core.social.qq.connect;

import com.daniel.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * on 6/4/2018.
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ>{
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}
