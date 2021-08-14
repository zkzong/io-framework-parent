package com.zkzong.framework.core.annotation.impl;

import com.zkzong.framework.core.annotation.IdentityCardNumber;
import com.zkzong.framework.core.utils.IdCardValidatorUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentityCardNumberValidator implements ConstraintValidator<IdentityCardNumber, String> {

    @Override
    public void initialize(IdentityCardNumber identityCardNumber) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return IdCardValidatorUtils.isValidate18Idcard(s);
    }
}

