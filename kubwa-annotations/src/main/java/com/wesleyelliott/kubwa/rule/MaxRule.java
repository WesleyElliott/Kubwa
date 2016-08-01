package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class MaxRule extends Rule<Integer> {

    private int maxValue;

    public MaxRule(Integer maxValue) {
        super(Integer.class);
        this.maxValue = maxValue;
    }

    @Override
    public boolean isValid(Integer value) {
        return value != null && value < maxValue;
    }
}
