package com.wesleyelliott.kubwa.annotation;

import com.wesleyelliott.kubwa.rule.CheckedRule;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by wesley on 2016/07/28.
 */
@ValidateUsing(CheckedRule.class)
@Retention(RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER , TYPE})
public @interface Checked {
    boolean value() default true;
    int errorMessage();
    String name() default "checkedError";

    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Checked[] value();
    }

}
