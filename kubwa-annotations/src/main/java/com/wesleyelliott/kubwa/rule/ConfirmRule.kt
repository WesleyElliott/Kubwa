package com.wesleyelliott.kubwa.rule

/**
 * Created by wesley on 2016/07/28.
 */

abstract class ConfirmRule<T>(type: Class<T>) : Rule<T>(type) {

    abstract fun isValid(value1: T?, value2: T?): Boolean

    override fun isValid(value: T?): Boolean {
        return isValid(value, value)
    }
}
