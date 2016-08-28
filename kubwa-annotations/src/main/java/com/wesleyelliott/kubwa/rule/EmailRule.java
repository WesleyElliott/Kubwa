package com.wesleyelliott.kubwa.rule;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by wesley on 2016/07/28.
 */

public class EmailRule extends Rule<String> {

    public EmailRule() {
        super(String.class);
    }

    @Override
    public boolean isValid(String value) {
        return (!TextUtils.isEmpty(value) && Patterns.EMAIL_ADDRESS.matcher(value).matches());
    }
}
