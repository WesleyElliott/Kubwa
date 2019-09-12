package com.wesleyelliott.kubwa

import java.util.regex.Pattern

object Utils {

    fun isEmpty(str: String?): Boolean {
        return str == null || str.isEmpty()
    }

    val EMAIL_ADDRESS = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}