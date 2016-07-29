package com.wesleyjasonelliott.kubwa.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wesley on 2016/07/28.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ValidateUsing {
    Class<? extends com.wesleyjasonelliott.kubwa.rule.Rule> value();
}