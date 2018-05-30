package com.daniel.security.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Daniel on 2018/5/27.
 *
 * 自定义登录认证逻辑
 */
@Component
public class MyUserDetailsService implements UserDetailsService{
    private Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    //@Autowired
    //private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("username :" + username);
        //User user = userDao.getUserByUsername(username;)

        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        String encodePassword = passwordEncoder.encode("123456");
        logger.info("database encodePassword :" + encodePassword);

        //return new MyUserDetails();
        return new User(username, encodePassword, authorities);
    }
}