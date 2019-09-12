package com.wesleyelliott.kubwa.rule

import android.text.TextUtils

/**
 * Created by wesley on 2016/07/28.
 */

open class RegexRule(protected val regex: String) : Rule<String>(String::class.java) {

    override fun isValid(value: String?): Boolean {
        return value != null && !TextUtils.isEmpty(value) && value.matches(regex.toRegex())
    }
}
