package com.wesleyelliott.kubwa.rule;

import android.text.TextUtils;

/**
 * Created by wesley on 2016/07/28.
 */

public class NotNullRule extends Rule<String> {

    public NotNullRule() {
        super(String.class);
    }

    @Override
    public boolean isValid(String value) {
        return !TextUtils.isEmpty(value);
    }
}
