package com.wesleyelliott.kubwa.annotation

import com.wesleyelliott.kubwa.rule.MaxRule

/**
 * Created by wesley on 2016/07/28.
 */
@ValidateUsing(MaxRule::class)
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.CLASS,
    AnnotationTarget.FILE
)
annotation class Max(val value: Int = 0, val errorMessage: Int, val name: String = "maxError") {

    @Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
    @Retention(AnnotationRetention.RUNTIME)
    @MustBeDocumented
    annotation class List(vararg val value: Max)
}
