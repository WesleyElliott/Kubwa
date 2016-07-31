package com.wesleyelliott.kubwa;

import com.wesleyelliott.kubwa.rule.PasswordRule;
import com.wesleyelliott.kubwa.rule.Rule;

/**
 * Created by wesley on 2016/07/28.
 */

public class PasswordFieldRule {

    public String fieldName;
    public Class<? extends Rule> fieldRuleType;
    public int fieldErrorResource;
    public Rule fieldRule;

    // Annotation Specific variables

    // Password:
    public PasswordRule.Scheme passwordScheme;

    // Regex
    public String regex;

    // Checked
    public Boolean checkedValue;

    // Confirm
    public String other;

    public String getFieldName() {
        return fieldName + "Validation";
    }

    public String getMethodName() {
        return "validate" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
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
