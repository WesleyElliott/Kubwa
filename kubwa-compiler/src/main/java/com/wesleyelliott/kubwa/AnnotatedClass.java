package com.wesleyelliott.kubwa;

import com.wesleyelliott.kubwa.fieldrule.FieldRule;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;

/**
 * Created by wesley on 2016/07/28.
 */
public class AnnotatedClass {
    public final String annotatedClassName;
    public final TypeElement typeElement;
    public List<FieldRule> fieldRules;

    public AnnotatedClass(TypeElement typeElement) {
        this.annotatedClassName = typeElement.getSimpleName().toString();
        this.typeElement = typeElement;
        this.fieldRules = new ArrayList<>();
    }

    public void addFieldRules(List<FieldRule> fieldRules) {
        this.fieldRules.addAll(fieldRules);
    }
}
