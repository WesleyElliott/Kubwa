package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class IdNumberRule extends RegexRule {

    public IdNumberRule() {
        super("(\\d){13}");
    }
}
