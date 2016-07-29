package com.wesleyjasonelliott.kubwa;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.wesleyjasonelliott.kubwa.annotation.Email;
import com.wesleyjasonelliott.kubwa.annotation.FullName;
import com.wesleyjasonelliott.kubwa.annotation.IdNumber;
import com.wesleyjasonelliott.kubwa.annotation.MobileNumber;
import com.wesleyjasonelliott.kubwa.annotation.NotNull;
import com.wesleyjasonelliott.kubwa.annotation.Password;
import com.wesleyjasonelliott.kubwa.annotation.ValidateUsing;
import com.wesleyjasonelliott.kubwa.rule.Rule;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import static com.squareup.javapoet.JavaFile.builder;
import static javax.lang.model.SourceVersion.latestSupported;

@AutoService(Processor.class)
public class KubwaCompiler extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getAllSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }

        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return latestSupported();
    }

    private Set<Class<? extends Annotation>> getAllSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.addAll(getSupportedAnnotations());
        annotations.addAll(getSupportedAnnotationsList());
        return annotations;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();


        annotations.add(Email.class);
        annotations.add(FullName.class);
        annotations.add(Password.class);
        annotations.add(IdNumber.class);
        annotations.add(MobileNumber.class);
        annotations.add(NotNull.class);

        return annotations;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotationsList() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(Email.List.class);
        annotations.add(Password.List.class);

        return annotations;
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<TypeElement, AnnotatedClass> annotatedClasses = processTargets(roundEnv);

        try {
            generate(annotatedClasses);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Map<TypeElement, AnnotatedClass> processTargets(RoundEnvironment env) {
        Map<TypeElement, AnnotatedClass> annotatedClasses = new HashMap<>();

        for (Class<? extends Annotation> supportedAnnotation : getAllSupportedAnnotations()) {
            for (Element element : env.getElementsAnnotatedWith(supportedAnnotation)) {
                if (element.getKind() == ElementKind.CLASS) {
                    try {

                        TypeElement typeElement = (TypeElement) element;
                        AnnotatedClass annotatedClass;
                        List<FieldRule> fieldRules = new ArrayList<>();

                        if (getSupportedAnnotations().contains(supportedAnnotation)) {
                            // Single Annotation
                            fieldRules = parseSingle(typeElement, supportedAnnotation);
                        } else if (getSupportedAnnotationsList().contains(supportedAnnotation)) {
                            // List of Annotations
                            fieldRules = parseList(typeElement, supportedAnnotation);
                        }

                        // Prevent duplicating class with new annotations, rather append
                        if (annotatedClasses.containsKey(typeElement)) {
                            annotatedClass = annotatedClasses.get(typeElement);
                        } else {
                            annotatedClass = new AnnotatedClass(typeElement);
                        }

                        annotatedClass.addFieldRules(fieldRules);
                        annotatedClasses.put(typeElement, annotatedClass);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        return annotatedClasses;
    }

    private<T extends Annotation> List<FieldRule> parseSingle(TypeElement typeElement, Class<T> annotationType) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<FieldRule> fieldRules = new ArrayList<>();

        T annotation = typeElement.getAnnotation(annotationType);
        FieldRule fieldRule = new FieldRule();
        fieldRule.fieldName = (String) annotation.annotationType().getMethod("name").invoke(annotation);
        fieldRule.fieldErrorResource = (int) annotation.annotationType().getMethod("errorMessage").invoke(annotation);
        fieldRule.fieldRule = getRuleType(annotation);

        fieldRules.add(fieldRule);
        return fieldRules;
    }

    private<T extends Annotation> List<FieldRule> parseList(TypeElement typeElement, Class<T> annotationType) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<FieldRule> fieldRules = new ArrayList<>();
        T annotationParent = typeElement.getAnnotation(annotationType);

        T[] annotationList = (T[]) annotationParent.annotationType().getMethod("value").invoke(annotationParent);

        for (T annotation : annotationList) {
            FieldRule fieldRule = new FieldRule();
            fieldRule.fieldName = (String) annotation.annotationType().getMethod("name").invoke(annotation);
            fieldRule.fieldErrorResource = (int) annotation.annotationType().getMethod("errorMessage").invoke(annotation);
            fieldRule.fieldRule = getRuleType(annotation);

            fieldRules.add(fieldRule);
        }


        return fieldRules;
    }


    private Class<? extends Rule> getRuleType(Annotation ruleAnnotation) {
        ValidateUsing validateUsing = ruleAnnotation.annotationType().getAnnotation(ValidateUsing.class);
        return validateUsing != null ? validateUsing.value() : null;
    }

    private void generate(Map<TypeElement, AnnotatedClass> annos) throws IOException {
        if (annos.size() == 0) {
            return;
        }

        for (AnnotatedClass annotatedClass : annos.values()) {

            System.out.println(annotatedClass.fieldRules.size());


            String packageName = getPackageName(processingEnv.getElementUtils(), annotatedClass.typeElement);
            TypeSpec generatedClass = CodeGenerator.generateClass(annotatedClass);

            JavaFile javaFile = builder(packageName, generatedClass).build();
            javaFile.writeTo(processingEnv.getFiler());
        }

    }

    private String getPackageName(Elements elementUtils, TypeElement type) {
        PackageElement pkg = elementUtils.getPackageOf(type);
        if (pkg.isUnnamed()) {
            // OOps....
        }
        return pkg.getQualifiedName().toString();
    }
}
