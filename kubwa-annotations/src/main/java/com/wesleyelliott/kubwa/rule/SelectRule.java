package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class SelectRule extends Rule<Integer> {

    private int spinnerMinValue;

    public SelectRule(Integer spinnerMinValue) {
        super(Integer.class);
        this.spinnerMinValue = spinnerMinValue;
    }

    @Override
    public boolean isValid(Integer value) {
        return value != null && value > spinnerMinValue;
    }
}
