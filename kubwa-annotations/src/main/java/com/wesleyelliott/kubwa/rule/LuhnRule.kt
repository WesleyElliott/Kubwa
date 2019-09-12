package com.wesleyelliott.kubwa.rule

import com.wesleyelliott.kubwa.Utils

/**
 * Created by wesley on 2016/08/04.
 */

abstract class LuhnRule(private val modulus: Int) : Rule<String>(String::class.java) {

    @JvmOverloads
    fun validate(value: String, regex: String = ".+", includesCheckDigit: Boolean = true): Boolean {
        // Validate Regex
        if (Utils.isEmpty(value) || !value.matches(regex.toRegex())) {
            return false
        }

        var total = 0
        for (i in value.indices) {
            val lth = value.length + if (includesCheckDigit) 0 else 1
            val leftPos = i + 1
            val rightPos = lth - i
            val charValue = toInt(value[i])
            total += weightedValue(charValue, leftPos, rightPos)
        }
        return if (total == 0) {
            false
        } else total % modulus == 0
    }

    private fun weightedValue(charValue: Int, leftPos: Int, rightPos: Int): Int {
        val POSITION_WEIGHT = intArrayOf(2, 1)
        val weight = POSITION_WEIGHT[rightPos % 2]
        val weightedValue = charValue * weight
        return if (weightedValue > 9) weightedValue - 9 else weightedValue
    }

    private fun toInt(character: Char): Int {
        return if (Character.isDigit(character)) {
            Character.getNumericValue(character)
        } else -1
    }

}
