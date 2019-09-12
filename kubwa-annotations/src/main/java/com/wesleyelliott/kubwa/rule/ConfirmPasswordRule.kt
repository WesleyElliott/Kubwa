package com.wesleyelliott.kubwa.rule

/**
 * Created by wesley on 2016/07/28.
 */

class ConfirmPasswordRule : ConfirmRule<String>(String::class.java) {

    override fun isValid(value1: String?, value2: String?): Boolean {
        return value1 != null && value2 != null && value1 == value2
    }
}
