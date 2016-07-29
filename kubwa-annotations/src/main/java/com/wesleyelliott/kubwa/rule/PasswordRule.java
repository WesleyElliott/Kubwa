package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class PasswordRule extends RegexRule {

    @Override
    protected String getRegex() {
        return "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).+";
    }
}
