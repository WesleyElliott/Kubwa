package com.wesleyjasonelliott.kubwa.annotation;

import com.wesleyjasonelliott.kubwa.rule.FullNameRule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wesley on 2016/07/28.
 */
@ValidateUsing(FullNameRule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FullName {
    int errorMessage();
}
