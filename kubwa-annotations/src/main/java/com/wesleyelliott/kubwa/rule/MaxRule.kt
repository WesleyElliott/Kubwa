package com.wesleyelliott.kubwa.rule

/**
 * Created by wesley on 2016/07/28.
 */

class MaxRule(private val maxValue: Int) : Rule<Int>(Int::class.java) {

    override fun isValid(value: Int?): Boolean {
        return value != null && value < maxValue
    }
}
