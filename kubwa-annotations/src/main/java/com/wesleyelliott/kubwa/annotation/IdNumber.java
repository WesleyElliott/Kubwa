package com.wesleyelliott.kubwa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wesley on 2016/07/28.
 */
@ValidateUsing(com.wesleyelliott.kubwa.rule.IdNumberRule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IdNumber {
    int errorMessage();
}
