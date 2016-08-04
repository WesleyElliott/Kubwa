package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/08/04.
 */

public class TaxNumberRule extends LuhnRule {

    public TaxNumberRule() {
        super(10);
    }

    @Override
    public boolean isValid(String value) {
        return validate(value);
    }
}
