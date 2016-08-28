package com.wesleyelliott.kubwa;

import com.wesleyelliott.kubwa.fieldrule.FieldRule;
import com.wesleyelliott.kubwa.rule.Rule;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

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

    public static<T extends FieldRule> T getRule(List<T> fieldRuleList, Class<? extends Rule> ruleType) {
        for (T fieldRule : fieldRuleList) {
            if (fieldRule.fieldRuleType.equals(ruleType)) {
                return fieldRule;
            }
        }
        return null;
    }

    public static boolean classHasNameDuplicates(Set<? extends Element> elements, List<Class<? extends Annotation>> supportedAnnotations) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Set<String> annotationNames = new HashSet<>();

        for (Element element : elements) {
            for (Class<? extends Annotation> annotation : supportedAnnotations) {
                TypeElement typeElement = (TypeElement) element;
                String annotationName = (String) annotation.getMethod("name").invoke(typeElement.getAnnotation(annotation));

                if (annotationNames.contains(annotationName)) {
                    return true;
                }

                annotationNames.add(annotationName);
            }
        }

        return false;
    }
}
