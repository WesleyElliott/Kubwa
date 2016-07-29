package com.wesleyjasonelliott.kubwa.rule;

import android.text.TextUtils;

/**
 * Created by wesley on 2016/07/28.
 */

public abstract class RegexRule extends Rule {

    protected abstract String getRegex();

    @Override
    public boolean isValid(String value) {
        return !TextUtils.isEmpty(value) && value.matches(getRegex());
    }
}
