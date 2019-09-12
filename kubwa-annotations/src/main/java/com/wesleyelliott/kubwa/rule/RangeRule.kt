package com.wesleyelliott.kubwa.rule

/**
 * Created by wesley on 2016/07/28.
 */

class RangeRule(
    private val minValue: Int,
    private val maxValue: Int,
    private val includeBounds: Boolean
) : Rule<Int>(Int::class.java) {

    override fun isValid(value: Int?): Boolean {
        return if (includeBounds) {
            value != null && value >= minValue && value <= maxValue
        } else {
            value != null && value > minValue && value < maxValue
        }
    }
}
