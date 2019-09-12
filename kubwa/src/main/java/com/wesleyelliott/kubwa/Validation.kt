package com.wesleyelliott.kubwa

import android.content.Context
import com.wesleyelliott.kubwa.rule.ConfirmRule
import com.wesleyelliott.kubwa.rule.Rule

class Validation<T>(
    private val context: Context,
    private val errorMessageId: Int,
    private val rule: Rule<T>
) {
    var message: String? = null

    fun validate(value: T) {
        message = if (!rule.isValid(value)) {
            if (errorMessageId != -1) context.getString(errorMessageId) else "Error"
        } else {
            null
        }
    }

    fun validate(value: T?, value2: T?) {
        val confirmRule = rule as ConfirmRule<T>
        message = if (!confirmRule.isValid(value, value2)) {
            if (errorMessageId != -1) context.getString(errorMessageId) else "Error"
        } else {
            null
        }
    }

}
