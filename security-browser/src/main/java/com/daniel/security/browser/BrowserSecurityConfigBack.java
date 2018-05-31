package com.daniel.security.browser;

import com.daniel.security.browser.handler.CustomizedAuthenticationFailureHandler;
import com.daniel.security.browser.handler.CustomizedAuthenticationSuccessHandler;
import com.daniel.security.core.config.SmsCodeAuthenticationSecurityConfig;
import com.daniel.security.core.filter.SmsCodeFilter;
import com.daniel.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
//@Configuration
public class BrowserSecurityConfigBack extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityProperties securityProperties;
    @Autowired
    CustomizedAuthenticationSuccessHandler successHandler;
    @Autowired
    CustomizedAuthenticationFailureHandler failureHandler;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        //validateCodeFilter.setFailureHandler(failureHandler);
        //validateCodeFilter.setSecurityProperties(securityProperties);
        //validateCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setFailureHandler(failureHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();

        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)    //把自定义的过滤器添加到springSecurity过滤器链的指定位置
                .formLogin()
                    .loginPage("/authenticate/require")         //在此处确定跳转的认证页面地址
                    .loginProcessingUrl("/authenticate/form")   //登录页面表单提交地址
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                .and()
                    .rememberMe()
                    .tokenRepository(persistentTokenRepository())   //指定tokenRepository 用于读取和写入token
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())       //token过期时间
                    .userDetailsService(userDetailsService)         //查询到token后  处理校验逻辑的UserDetailsService实现类
                .and()
                    .authorizeRequests()    //authorize 授权
                    .antMatchers("/authenticate/require",
                            "/code/*",      // /code/image,/code/sms
                            securityProperties.getBrowser().getLoginPage()
                    ).permitAll()
                    .anyRequest()
                    .authenticated()       //authenticate 认证
                .and()
                    .csrf().disable()
                .apply(smsCodeAuthenticationSecurityConfig);
    }

    /**
     * 密码加密 验证
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入
     * 持久化操作remember me的token的TokenRepository
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        //setDataSource
        repository.setDataSource(dataSource);
        //create table on startup
        //repository.setCreateTableOnStartup(true);
        return repository;
    }
 }
