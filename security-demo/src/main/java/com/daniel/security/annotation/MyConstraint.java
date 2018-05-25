package com.daniel.security.annotation;

import com.daniel.security.validator.MyConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * on 5/24/2018.
 *
 * 自定义校验注解
 *
 * 通过@Constraint的validatedBy属性指定具体校验逻辑的实现类
 *
 * 校验注解必须有 message groups  payload 三个属性
 *
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)     //validatedBy 指定校验逻辑的实现类
public @interface MyConstraint {

    //校验失败的信息
    String message() default "用户名已经被占用,请选择其他名称!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
