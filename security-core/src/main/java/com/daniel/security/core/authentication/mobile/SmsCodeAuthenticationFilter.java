package com.daniel.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Daniel on 2018/5/29.
 *
 * 短信登录方式认证过滤器
 *
 * 功能 扩展springSecurity支持 手机号+短信验证码 方式登录
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final String DANIEL_FORM_MOBILE_KEY = "mobile";

    private String mobileParameter = DANIEL_FORM_MOBILE_KEY;

    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/authenticate/mobile", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);
        if (mobile == null)
            mobile = "";
        mobile = mobile.trim();

        //初始化未授权的Token
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        //把未授权的Token传给AuthenticationManager
        //由AuthenticationManager找支持此种登录方式的AuthenticationProvider对象[此处即为:SmsCodeAuthenticationProvider]执行具体的登录认证逻辑
        //并返回Provider的认证结果 即Authentication对象
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 获取请求参数中的手机号
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    /**
     * 把请求相关的信息设置到Token中
     *
     * @param request
     * @param authRequest
     */
    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.mobileParameter = usernameParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }
}
