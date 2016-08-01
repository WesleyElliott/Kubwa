package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class MinRule extends Rule<Integer> {

    private int minValue;

    public MinRule(Integer minValue) {
        super(Integer.class);
        this.minValue = minValue;
    }

    @Override
    public boolean isValid(Integer value) {
        return value != null && value > minValue;
    }
}
