package com.daniel.security.core.social.qq.api;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

/**
 * on 5/31/2018.
 *
 * accessToken 流程中步骤5由QQ服务提供商发放的令牌
 *
 * restTemplate 用于步骤6中向服务提供商发送Http请求获取用户信息
 *
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ{
    @Override
    public QQUserInfo getUserInfo() {
        return null;
    }
}
