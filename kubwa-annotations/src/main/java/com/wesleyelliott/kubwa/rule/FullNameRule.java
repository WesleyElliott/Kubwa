package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class FullNameRule extends RegexRule {

    public FullNameRule() {
        super("^([\\w-]+)\\s+([\\w\\s'-]+)+$");
    }
}
