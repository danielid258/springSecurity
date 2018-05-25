package com.daniel.security.validator;

import com.daniel.security.annotation.MyConstraint;
import com.daniel.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * on 5/24/2018.
 *
 * 自定义校验器 实现校验逻辑
 *
 * 实现javax.validation.ConstraintValidator接口
 *
 * 第一个泛型 自定义的校验注解
 *
 * 第二个泛型 需要校验的类型
 *
 * 特别地 在校验类中可以通过 @Autowired 注入任意对象 已实现校验逻辑 但是校验类不必加上@Service或@Component等注解
 *
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object> {
    @Autowired
    private UserService userService;

    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("execute my validator initialize ...");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        userService.hello();
        System.out.println(value);

        return false;
    }
}
