/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interviewtest.line.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author Dell G7
 */
public class EmailValidator implements ConstraintValidator<Email, Object> {

    private String regex;
    private String message;

    @Override
    public void initialize(Email data) {
        /**
         * https://howtodoinjava.com/regex/java-regex-validate-email-address/
         */
        regex = ".*".equals(data.regexp()) ? "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$" : data.regexp();
        message = data.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext cvc) {
        if (message == null || "".equals(message) || "{ConfirmPassword.message}".equals(message)) {
            message = "Invalid Email!";
            cvc.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value.toString()).matches();
    }

}
