package com.wesleyelliott.kubwa.annotation

import com.wesleyelliott.kubwa.rule.NotNullRule

/**
 * Created by wesley on 2016/07/28.
 */
@ValidateUsing(NotNullRule::class)
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
annotation class NotNull(val errorMessage: Int, val name: String = "notNullError") {

    @Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
    @Retention(AnnotationRetention.RUNTIME)
    @MustBeDocumented
    annotation class List(vararg val value: NotNull)
}
