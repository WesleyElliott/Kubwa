package com.wesleyelliott.kubwa.rule;

/**
 * Created by wesley on 2016/07/28.
 */

public abstract class Rule<T> {

    private Class<T> type;

    public Rule(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    public abstract boolean isValid(T value);

}
