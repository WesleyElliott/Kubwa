package com.wesleyelliott.kubwa.rule

import com.wesleyelliott.kubwa.Utils


/**
 * Created by wesley on 2016/07/28.
 */

class NotNullRule : Rule<String>(String::class.java) {

    override fun isValid(value: String?): Boolean {
        return !Utils.isEmpty(value)
    }
}
