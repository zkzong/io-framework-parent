package io.code.framework.core.annotation.impl;

import io.code.framework.core.annotation.IdentityCardNumber;
import io.code.framework.core.utils.IdCardValidatorUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class IdentityCardNumberValidator implements ConstraintValidator<IdentityCardNumber, String> {

    @Override
    public void initialize(IdentityCardNumber identityCardNumber) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return IdCardValidatorUtils.isValidate18Idcard(s);
    }
}

