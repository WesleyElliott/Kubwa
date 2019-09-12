package com.wesleyelliott.kubwa.rule

/**
 * Created by wesley on 2016/08/04.
 */

class TaxNumberRule : LuhnRule(10) {

    override fun isValid(value: String?): Boolean {
        return value != null && validate(value)
    }
}
