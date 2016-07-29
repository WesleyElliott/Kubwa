package com.wesleyjasonelliott.kubwa.annotation;

import com.wesleyjasonelliott.kubwa.rule.MobileNumberRule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wesley on 2016/07/28.
 */
@ValidateUsing(MobileNumberRule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MobileNumber {
    int errorMessage();
}
