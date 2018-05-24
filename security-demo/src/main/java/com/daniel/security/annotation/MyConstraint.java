package com.daniel.security.annotation;

import com.daniel.security.validator.MyConstraintValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * on 5/24/2018.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = MyConstraintValidator.class)     //
public @interface MyConstraint {

}
