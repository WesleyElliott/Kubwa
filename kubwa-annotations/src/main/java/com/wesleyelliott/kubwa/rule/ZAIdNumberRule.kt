package com.wesleyelliott.kubwa.rule

/**
 * Created by wesley on 2016/07/28.
 */

class ZAIdNumberRule : LuhnRule(10) {

    override fun isValid(value: String?): Boolean {
        return value != null && validate(value, "(\\d){13}")
    }

}
