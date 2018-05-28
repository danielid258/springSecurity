package com.daniel.security.browser;

import com.daniel.security.browser.authentication.CustomizedAuthenticationFailureHandler;
import com.daniel.security.browser.authentication.CustomizedAuthenticationSuccessHandler;
import com.daniel.security.core.filter.ValidateCodeFilter;
import com.daniel.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Daniel on 2018/5/27.
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityProperties securityProperties;
    @Autowired
    CustomizedAuthenticationSuccessHandler successHandler;
    @Autowired
    CustomizedAuthenticationFailureHandler failureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setFailureHandler(failureHandler);

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)    //把自定义的过滤器添加到springSecurity过滤器链的指定位置
                .formLogin()
                .loginPage("/authenticate/require")         //在此处确定跳转的认证页面地址
                .loginProcessingUrl("/authenticate/form")   //登录页面表单提交地址
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .authorizeRequests()    //authorize 授权
                .antMatchers("/authenticate/require",
                        "/code/image",
                        securityProperties.getBrowser().getLoginPage()
                ).permitAll()
                .anyRequest()
                .authenticated()       //authenticate 认证
                .and()
                .csrf().disable();
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
 }
