package com.fise.framework.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fise.framework.annotation.MaxLength;


/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-7-27
 * @desc 定义字段最大长度验证器
 */
public class MaxLengthValidator implements ConstraintValidator<MaxLength, String> {

    private int length;

    @Override
    public void initialize(MaxLength constraintAnnotation) {
        length = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
    	if (value == null)	return true;
        
    	return value.length() <= length;
    }
}
