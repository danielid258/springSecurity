package com.daniel.security.browser;

import com.daniel.security.core.config.AbstractChannelSecurityConfig;
import com.daniel.security.core.config.SmsCodeAuthenticationSecurityConfig;
import com.daniel.security.core.config.ValidateCodeSecurityConfig;
import com.daniel.security.core.filter.SmsCodeFilter;
import com.daniel.security.core.filter.ValidateCodeFilter;
import com.daniel.security.core.properties.SecurityConstants;
import com.daniel.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Daniel on 2018/5/27.
 */
@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {
    @Autowired
    SecurityProperties securityProperties;
    /**
     * Remember Me Datasource
     */
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * 验证码校验过滤器
     */
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    /**
     * 手机号+短信验证码认证过滤器
     */
    @Autowired
    SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //密码登录方式的公共配置
        applyPasswordAuthenticationConfig(http);
        http.apply(validateCodeSecurityConfig)                      //验证码校验Filter的注册配置
            .and()
            .apply(smsCodeAuthenticationSecurityConfig)             //扩展springSecurity认证方式: 手机号+短信验证码
            .and()
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
            .and()
            .authorizeRequests()
                .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                    SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                    SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                    securityProperties.getBrowser().getLoginPage()
                    ).permitAll()
                .anyRequest()
                .authenticated()
            .and()
            .csrf()
                .disable();
    }

    /**
     * PasswordEncoder
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * PersistentTokenRepository
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        //repository.setCreateTableOnStartup(true);
        return repository;
    }
 }
