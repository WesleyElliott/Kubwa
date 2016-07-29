package com.wesleyelliott.kubwa;

import android.content.Context;

import com.wesleyelliott.kubwa.rule.Rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class Validation {

    private Context context;
    private String message = null;
    private int errorMessageId;
    private Rule rule;

    public Validation(Context context, int errorMessageId, Rule rule) {
        this.context = context;
        this.errorMessageId = errorMessageId;
        this.rule = rule;
    }

    public Validation() {
    }

    public void init(Context context, int errorMessageId, Rule rule) {
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
