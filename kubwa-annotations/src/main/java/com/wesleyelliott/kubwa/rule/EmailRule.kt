package com.wesleyelliott.kubwa.rule

import android.text.TextUtils
import android.util.Patterns

/**
 * Created by wesley on 2016/07/28.
 */

class EmailRule : Rule<String>(String::class.java) {

    override fun isValid(value: String?): Boolean {
        return value != null
                && !TextUtils.isEmpty(value)
                && Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}
