package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class CheckedRule extends Rule<Boolean> {

    private boolean value;

    public CheckedRule(Boolean value) {
        super(Boolean.class);
        this.value = value;
    }

    @Override
    public boolean isValid(Boolean value) {
        return value != null && this.value == value;
    }
}
