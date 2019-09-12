package com.wesleyelliott.kubwa.rule

/**
 * Created by wesley on 2016/07/28.
 */

class MinRule(private val minValue: Int) : Rule<Int>(Int::class.java) {

    override fun isValid(value: Int?): Boolean {
        return value != null && value > minValue
    }
}
