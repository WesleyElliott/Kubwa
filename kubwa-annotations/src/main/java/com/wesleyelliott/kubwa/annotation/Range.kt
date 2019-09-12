package com.wesleyelliott.kubwa.annotation

import com.wesleyelliott.kubwa.rule.RangeRule

/**
 * Created by wesley on 2016/07/28.
 */
@ValidateUsing(RangeRule::class)
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
annotation class Range(
    val errorMessage: Int,
    val name: String = "rangeError",
    val min: Int = Integer.MIN_VALUE,
    val max: Int = Integer.MAX_VALUE,
    val includeBounds: Boolean = false
) {

    @Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
    @Retention(AnnotationRetention.RUNTIME)
    @MustBeDocumented
    annotation class List(vararg val value: Range)
}
