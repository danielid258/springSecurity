package com.daniel.security.core.config;

import com.daniel.security.core.authentication.mobile.SmsCodeAuthenticationFilter;
import com.daniel.security.core.authentication.mobile.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * Daniel on 2018/5/29.
 *
 * 扩展springSecurity认证方式: 手机号+短信验证码
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private AuthenticationSuccessHandler customizedAuthenticationFailureHandler;

    @Autowired
    private AuthenticationFailureHandler customizedAuthenticationSuccessHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        //set AuthenticationManager
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        //set success and failure handler
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(customizedAuthenticationFailureHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(customizedAuthenticationSuccessHandler);

        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        //set UserDetailsService for provider
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

        //register provider
        http.addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)        //把扩展认证方式的过滤器加到过滤器链上
            .authenticationProvider(smsCodeAuthenticationProvider);                                         //注册该认证方式的 AuthenticationProvider
    }
}
