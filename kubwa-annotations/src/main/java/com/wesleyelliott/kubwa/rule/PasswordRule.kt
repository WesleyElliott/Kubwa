package com.wesleyelliott.kubwa.rule

/**
 * Created by wesley on 2016/07/28.
 */

class PasswordRule(passwordScheme: Scheme) : RegexRule(passwordScheme.regex) {

    /*
     * Thanks to Saripaar library for these Regex's
     */
    enum class Scheme private constructor(val regex: String) {
        ANY(".+"),
        ALPHA("\\w+"),
        ALPHA_MIXED_CASE("(?=.*[a-z])(?=.*[A-Z]).+"),
        NUMERIC("\\d+"),
        ALPHA_NUMERIC("(?=.*[a-zA-Z])(?=.*[\\d]).+"),
        ALPHA_NUMERIC_MIXED_CASE("(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).+"),
        ALPHA_NUMERIC_SYMBOLS("(?=.*[a-zA-Z])(?=.*[\\d])(?=.*([^\\w])).+"),
        ALPHA_NUMERIC_MIXED_CASE_SYMBOLS("(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*([^\\w])).+")
    }
}
