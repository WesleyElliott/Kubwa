package com.wesleyelliott.kubwa.fieldrule;

import com.wesleyelliott.kubwa.rule.Rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class FieldRule {

    public String fieldName;
    public Class<? extends Rule> fieldRuleType;
    public int fieldErrorResource;
    public Rule fieldRule;

    public String getFieldName() {
        return fieldName + "Validation";
    }

    public String getMethodName() {
        String validateMethodName = fieldName.replace("Error", "");
        return "validate" + Character.toUpperCase(validateMethodName.charAt(0)) + validateMethodName.substring(1);
    }

    public String getErrorMessageMethodName() {
        return "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1) + "Message";
    }

    public String setErrorMessageMethodName() {
        return "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1) + "Message";
    }

    public String getValueName() {
        return fieldName.replace("Error", "") + "Value";
    }
}
