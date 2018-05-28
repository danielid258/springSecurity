package com.daniel.security.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * on 5/28/2018.
 *
 * AuthenticationException 认证过程中可能抛出的所有Exception的基类
 */
public class ValidateCodeException extends AuthenticationException{
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
