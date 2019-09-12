package com.wesleyelliott.kubwa.rule

abstract class Rule<T>(
    val type: Class<T>
) {
   abstract fun isValid(value: T?): Boolean
}