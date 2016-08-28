package com.wesleyelliott.kubwa.annotation;

import com.wesleyelliott.kubwa.rule.RangeRule;

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
@ValidateUsing(RangeRule.class)
@Retention(RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER , TYPE})
public @interface Range {
    int errorMessage();
    String name() default "rangeError";
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
    boolean includeBounds() default false;

    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Range[] value();
    }
}
