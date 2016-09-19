package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class ZAIdNumberRule extends LuhnRule {

    public ZAIdNumberRule() {
        super(10);
    }

    @Override
    public boolean isValid(String value) {
        return validate(value, "(\\d){13}");
    }

}
