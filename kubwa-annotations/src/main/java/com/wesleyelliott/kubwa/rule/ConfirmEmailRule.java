package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class ConfirmEmailRule extends ConfirmRule<String> {

    public ConfirmEmailRule() {
        super(String.class);
    }

    @Override
    public boolean isValid(String value1, String value2) {
        return value1 != null && value2 != null && value1.equals(value2);
    }
}
