package br.com.jrnb.webflux.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TrimStringValidator implements ConstraintValidator<TrimString, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       return value == null || value.isEmpty() || value.trim().length() == value.length();
    }
}
