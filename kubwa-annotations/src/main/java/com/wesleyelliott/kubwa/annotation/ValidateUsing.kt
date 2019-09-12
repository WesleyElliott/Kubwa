package com.wesleyelliott.kubwa.annotation


import com.wesleyelliott.kubwa.rule.Rule

import kotlin.reflect.KClass

/**
 * Created by wesley on 2016/07/28.
 */

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class ValidateUsing(val value: KClass<out Rule<*>>)