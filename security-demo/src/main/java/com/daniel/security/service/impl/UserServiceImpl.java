package com.daniel.security.service.impl;

import com.daniel.security.service.UserService;
import org.springframework.stereotype.Service;

/**
 * on 5/24/2018.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void hello() {
        System.out.println("greeting");
    }
}
