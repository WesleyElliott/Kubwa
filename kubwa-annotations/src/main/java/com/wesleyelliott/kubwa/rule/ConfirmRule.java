package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public abstract class ConfirmRule<T> extends Rule<T> {


    public ConfirmRule(Class type) {
        super(type);
    }

    public abstract boolean isValid(T value1, T value2);

    @Override
    public boolean isValid(T value) {
        return isValid(value, value);
    }
}
