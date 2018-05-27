package com.daniel.security.browser;

import com.daniel.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Daniel on 2018/5/27.
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityProperties securityProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/authenticate/require")         //在此处确定跳转的认证页面地址
                .loginProcessingUrl("/authenticate/form")   //UsernamePasswordAuthenticationFilter处理提交到这个地址的登录信息
                .and()
                .authorizeRequests()    //authorize 授权
                .antMatchers("/authenticate/require"
                        ,securityProperties.getBrowser().getLoginPage()
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
