package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class PasswordRule extends RegexRule {

    public PasswordRule(Scheme passwordScheme) {
        super(passwordScheme.getRegex());
    }

    /*
     * Thanks to Saripaar library for these Regex's
     */
    public enum Scheme {
        ANY(".+"),
        ALPHA("\\w+"),
        ALPHA_MIXED_CASE("(?=.*[a-z])(?=.*[A-Z]).+"),
        NUMERIC("\\d+"),
        ALPHA_NUMERIC("(?=.*[a-zA-Z])(?=.*[\\d]).+"),
        ALPHA_NUMERIC_MIXED_CASE("(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).+"),
        ALPHA_NUMERIC_SYMBOLS("(?=.*[a-zA-Z])(?=.*[\\d])(?=.*([^\\w])).+"),
        ALPHA_NUMERIC_MIXED_CASE_SYMBOLS("(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*([^\\w])).+");

        private String regex;

        Scheme(String regex) {
            this.regex = regex;
        }

        public String getRegex() {
            return regex;
        }
    }
}
