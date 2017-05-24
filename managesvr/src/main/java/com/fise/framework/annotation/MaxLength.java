package com.fise.framework.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;

import com.fise.framework.validator.MaxLengthValidator;

import java.lang.annotation.*;

/**
 * 最大长度注解
 *
 * @author huangyong
 * @since 1.0.0
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxLengthValidator.class)
public @interface MaxLength {

    String message() default "exceed max length limit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();
}
