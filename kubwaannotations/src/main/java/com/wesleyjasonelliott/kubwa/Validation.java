package com.wesleyjasonelliott.kubwa;

import android.content.Context;

/**
 * Created by wesley on 2016/07/28.
 */

public class Validation {

    private Context context;
    private String message = null;
    private int errorMessageId;
    private com.wesleyjasonelliott.kubwa.rule.Rule rule;

    public Validation(Context context, int errorMessageId, com.wesleyjasonelliott.kubwa.rule.Rule rule) {
        this.context = context;
        this.errorMessageId = errorMessageId;
        this.rule = rule;
    }

    public Validation() {
    }

    public void init(Context context, int errorMessageId, com.wesleyjasonelliott.kubwa.rule.Rule rule) {
        this.context = context;
        this.errorMessageId = errorMessageId;
        this.rule = rule;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void validate(String value) {
        if (!rule.isValid(value)) {
            message = errorMessageId != -1 ? context.getString(errorMessageId) : "Error";
        } else {
            message = null;
        }
    }

}
