package com.wesleyelliott.kubwa.rule

/**
 * Created by wesley on 2016/07/28.
 */

class CheckedRule(private val value: Boolean?) : Rule<Boolean>(Boolean::class.java) {

    override fun isValid(value: Boolean?): Boolean {
        return value != null && this.value == value
    }
}
