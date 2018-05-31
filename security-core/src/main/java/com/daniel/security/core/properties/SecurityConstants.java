package com.daniel.security.core.properties;

/**
 * on 5/30/2018.
 */
public interface SecurityConstants {
    /**
     * 默认的验证码url请求前缀
     */
    public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     * 请求需要身份认证时,默认跳转的url
     */
    public static final String DEFAULT_UNAUTHENTICATION_URL = "/authenticate/require";

    /**
     * 用户名密码登录 默认请求url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authenticate/form";

    /**
     * 手机验证码登录 默认请求url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authenticate/mobile";

    /**
     * 默认登录页面
     */
    public static final String DEFAULT_LOGIN_PAGE_URL = "/sign.html";

    /**
     * 验证图片验证码时,图片验证码的默认请求参数名
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    /**
     * 验证短信验证码时,短信验证码信息的默认请求参数名
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /**
     * 发送或验证 短信验证码时,手机号的默认请求参数名
     */
    public static final String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * 验证码放入session时的前缀
     */
    public static final String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * session失效后的默认跳转地址
     */
    public static final String DEFAULT_SESSION_INVALID_URL = "/session/invalid";
}
