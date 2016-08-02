package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class RangeRule extends Rule<Integer> {

    private int minValue;
    private int maxValue;
    private boolean includeBounds;

    public RangeRule(Integer minValue, Integer maxValue, Boolean includeBounds) {
        super(Integer.class);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.includeBounds = includeBounds;
    }

    @Override
    public boolean isValid(Integer value) {
        if (includeBounds) {
            return value != null && value >= minValue && value <= maxValue;
        } else {
            return value != null && value > minValue && value < maxValue;
        }
    }
}
