package com.wesleyelliott.kubwa.annotation;


import com.wesleyelliott.kubwa.rule.Rule;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by wesley on 2016/07/28.
 */

@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface ValidateUsing {
    Class<? extends Rule> value();
}