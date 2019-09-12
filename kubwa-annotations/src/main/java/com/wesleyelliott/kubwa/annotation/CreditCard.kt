package com.wesleyelliott.kubwa.annotation

import com.wesleyelliott.kubwa.rule.CreditCardRule

/**
 * Created by wesley on 2016/07/28.
 */
@ValidateUsing(CreditCardRule::class)
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
annotation class CreditCard(
    val errorMessage: Int,
    val name: String = "creditCardError",
    val types: Array<CreditCardRule.Type> = [CreditCardRule.Type.VISA, CreditCardRule.Type.MASTERCARD]
) {

    @Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
    @Retention(AnnotationRetention.RUNTIME)
    @MustBeDocumented
    annotation class List(vararg val value: CreditCard)

}
