package com.wesleyelliott.kubwa;

import com.wesleyelliott.kubwa.rule.Rule;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Created by wesley on 2016/07/31.
 */

public class Utils {

    public static boolean isAnnotationType(Class<? extends Annotation> annotation, Class type) {
        return annotation.getSimpleName().equals(type.getSimpleName());
    }

    public static boolean isRuleType(Class<? extends Rule> rule, Class type) {
        return rule.getSimpleName().equals(type.getSimpleName());
    }

    public static FieldRule getRule(List<FieldRule> fieldRuleList, Class<? extends Rule> ruleType) {
        for (FieldRule fieldRule : fieldRuleList) {
            if (fieldRule.fieldRuleType.equals(ruleType)) {
                return fieldRule;
            }
        }
        return null;
    }
}
