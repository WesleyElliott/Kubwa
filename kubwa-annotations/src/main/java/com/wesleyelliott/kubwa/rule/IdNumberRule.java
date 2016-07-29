package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class IdNumberRule extends RegexRule {

    @Override
    protected String getRegex() {
        return "(\\d){13}";
    }
}
