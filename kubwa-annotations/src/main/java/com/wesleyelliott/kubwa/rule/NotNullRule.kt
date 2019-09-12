package com.wesleyelliott.kubwa.rule

import android.text.TextUtils

/**
 * Created by wesley on 2016/07/28.
 */

class NotNullRule : Rule<String>(String::class.java) {

    override fun isValid(value: String?): Boolean {
        return !TextUtils.isEmpty(value)
    }
}
