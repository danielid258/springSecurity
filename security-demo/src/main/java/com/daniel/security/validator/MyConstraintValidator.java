package com.daniel.security.validator;

import com.daniel.security.annotation.MyConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * on 5/24/2018.
 *
 * 自定义校验器 实现自定义校验逻辑
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object> {
    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("execute initialize ...");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
