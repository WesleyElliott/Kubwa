package com.wesleyelliott.kubwa;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.wesleyelliott.kubwa.annotation.Checked;
import com.wesleyelliott.kubwa.annotation.ConfirmEmail;
import com.wesleyelliott.kubwa.annotation.ConfirmPassword;
import com.wesleyelliott.kubwa.annotation.CreditCard;
import com.wesleyelliott.kubwa.annotation.Email;
import com.wesleyelliott.kubwa.annotation.FullName;
import com.wesleyelliott.kubwa.annotation.Max;
import com.wesleyelliott.kubwa.annotation.Min;
import com.wesleyelliott.kubwa.annotation.MobileNumber;
import com.wesleyelliott.kubwa.annotation.NotNull;
import com.wesleyelliott.kubwa.annotation.Password;
import com.wesleyelliott.kubwa.annotation.Range;
import com.wesleyelliott.kubwa.annotation.Regex;
import com.wesleyelliott.kubwa.annotation.Select;
import com.wesleyelliott.kubwa.annotation.TaxNumber;
import com.wesleyelliott.kubwa.annotation.ValidateUsing;
import com.wesleyelliott.kubwa.annotation.ZAIdNumber;
import com.wesleyelliott.kubwa.fieldrule.CheckedFieldRule;
import com.wesleyelliott.kubwa.fieldrule.CreditCardFieldRule;
import com.wesleyelliott.kubwa.fieldrule.FieldRule;
import com.wesleyelliott.kubwa.fieldrule.MaxFieldRule;
import com.wesleyelliott.kubwa.fieldrule.MinFieldRule;
import com.wesleyelliott.kubwa.fieldrule.PasswordFieldRule;
import com.wesleyelliott.kubwa.fieldrule.RangeFieldRule;
import com.wesleyelliott.kubwa.fieldrule.RegexFieldRule;
import com.wesleyelliott.kubwa.fieldrule.SelectFieldRule;
import com.wesleyelliott.kubwa.rule.CreditCardRule;
import com.wesleyelliott.kubwa.rule.PasswordRule;
import com.wesleyelliott.kubwa.rule.RegexRule;
import com.wesleyelliott.kubwa.rule.Rule;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
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
import static javax.tools.Diagnostic.Kind.ERROR;

@AutoService(Processor.class)
public class KubwaCompiler extends AbstractProcessor {

    private Map<String, List<String>> processedRulesMap = new HashMap<>();

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
        annotations.add(ZAIdNumber.class);
        annotations.add(MobileNumber.class);
        annotations.add(NotNull.class);
        annotations.add(Regex.class);
        annotations.add(Checked.class);
        annotations.add(ConfirmEmail.class);
        annotations.add(ConfirmPassword.class);
        annotations.add(Min.class);
        annotations.add(Max.class);
        annotations.add(Select.class);
        annotations.add(Range.class);
        annotations.add(CreditCard.class);
        annotations.add(TaxNumber.class);

        return annotations;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotationsList() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(Email.List.class);
        annotations.add(FullName.List.class);
        annotations.add(Password.List.class);
        annotations.add(ZAIdNumber.List.class);
        annotations.add(MobileNumber.List.class);
        annotations.add(NotNull.List.class);
        annotations.add(Regex.List.class);
        annotations.add(Checked.List.class);
        annotations.add(ConfirmEmail.List.class);
        annotations.add(ConfirmPassword.List.class);
        annotations.add(Min.List.class);
        annotations.add(Max.List.class);
        annotations.add(Select.List.class);
        annotations.add(Range.List.class);
        annotations.add(CreditCard.List.class);
        annotations.add(TaxNumber.List.class);

        return annotations;
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("Testing");
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
                    TypeElement typeElement = (TypeElement) element;
                    AnnotatedClass annotatedClass;
                    List<FieldRule> fieldRules = new ArrayList<>();

                    try {

                        if (getSupportedAnnotations().contains(supportedAnnotation)) {
                            // Single Annotation
                            fieldRules.add(parseSingle(typeElement, typeElement.getAnnotation(supportedAnnotation)));
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
                    } catch (KubwaException kubwaE) {
                        processingEnv.getMessager().printMessage(ERROR, kubwaE.getMessage(), typeElement);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        return annotatedClasses;
    }

    private<T extends Annotation, FR extends FieldRule> FR parse(FR fieldRule, TypeElement typeElement, T annotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KubwaException {

        // Check we haven't hit a duplicate
        String annotationName = (String) annotation.annotationType().getMethod("name").invoke(annotation);
        String className = typeElement.getSimpleName().toString();

        List<String> classProcessedRules = processedRulesMap.get(className);
        if (classProcessedRules == null) {
            classProcessedRules = new ArrayList<>();
        }

        if (classProcessedRules.contains(annotationName)) {
            throw new KubwaException("Duplicate Validation Names found for " + annotation.annotationType().getSimpleName() + " : " + annotationName);
        }

        classProcessedRules.add(annotationName);
        processedRulesMap.put(className, classProcessedRules);

        fieldRule.fieldName = (String) annotation.annotationType().getMethod("name").invoke(annotation);
        fieldRule.fieldErrorResource = (int) annotation.annotationType().getMethod("errorMessage").invoke(annotation);
        fieldRule.fieldRuleType = getRuleType(annotation);
        fieldRule.fieldRule = createRule(fieldRule.fieldRuleType, annotation);

        return fieldRule;
    }

    private<T extends Annotation> CreditCardFieldRule parseCreditCard(TypeElement typeElement, T annotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KubwaException {
        CreditCardFieldRule fieldRule = parse(new CreditCardFieldRule(), typeElement, annotation);
        fieldRule.creditCardTypes = (CreditCardRule.Type[]) annotation.annotationType().getMethod("types").invoke(annotation);

        return fieldRule;
    }

    private<T extends Annotation> SelectFieldRule parseSpinner(TypeElement typeElement, T annotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KubwaException {
        SelectFieldRule fieldRule = parse(new SelectFieldRule(), typeElement, annotation);
        fieldRule.spinnerMinValue = (Integer) annotation.annotationType().getMethod("value").invoke(annotation);

        return fieldRule;
    }

    private<T extends Annotation> RangeFieldRule parseRange(TypeElement typeElement, T annotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KubwaException {
        RangeFieldRule fieldRule = parse(new RangeFieldRule(), typeElement, annotation);
        fieldRule.minValue = (Integer) annotation.annotationType().getMethod("min").invoke(annotation);
        fieldRule.maxValue = (Integer) annotation.annotationType().getMethod("max").invoke(annotation);
        fieldRule.includeBounds = (Boolean) annotation.annotationType().getMethod("includeBounds").invoke(annotation);

        return fieldRule;
    }

    private<T extends Annotation> MinFieldRule parseMin(TypeElement typeElement, T annotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KubwaException {
        MinFieldRule fieldRule = parse(new MinFieldRule(), typeElement, annotation);
        fieldRule.minValue = (Integer) annotation.annotationType().getMethod("value").invoke(annotation);

        return fieldRule;
    }

    private<T extends Annotation> MaxFieldRule parseMax(TypeElement typeElement, T annotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KubwaException {
        MaxFieldRule fieldRule = parse(new MaxFieldRule(), typeElement, annotation);
        fieldRule.maxValue = (Integer) annotation.annotationType().getMethod("value").invoke(annotation);

        return fieldRule;
    }

    private<T extends Annotation> CheckedFieldRule parseChecked(TypeElement typeElement, T annotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KubwaException {
        CheckedFieldRule fieldRule = parse(new CheckedFieldRule(), typeElement, annotation);
        fieldRule.checkedValue = (Boolean) annotation.annotationType().getMethod("value").invoke(annotation);

        return fieldRule;
    }

    private<T extends Annotation> PasswordFieldRule parsePassword(TypeElement typeElement, T annotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KubwaException {
        PasswordFieldRule fieldRule = parse(new PasswordFieldRule(), typeElement, annotation);
        fieldRule.passwordScheme = (PasswordRule.Scheme) annotation.annotationType().getMethod("scheme").invoke(annotation);

        return fieldRule;
    }

    private<T extends Annotation> RegexFieldRule parseRegex(TypeElement typeElement, T annotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KubwaException {
        RegexFieldRule fieldRule = parse(new RegexFieldRule(), typeElement, annotation);
        fieldRule.regex = (String) annotation.annotationType().getMethod("regex").invoke(annotation);

        return fieldRule;
    }

    private<T extends Annotation> FieldRule parseSingle(TypeElement typeElement, T annotation) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KubwaException {
        FieldRule fieldRule;

        if (Utils.isAnnotationType(annotation.annotationType(), Password.class)) {
            fieldRule = parsePassword(typeElement, annotation);
        } else if (Utils.isAnnotationType(annotation.annotationType(), Regex.class)) {
            fieldRule = parseRegex(typeElement, annotation);
        } else if (Utils.isAnnotationType(annotation.annotationType(), Checked.class)) {
            fieldRule = parseChecked(typeElement, annotation);
        } else if (Utils.isAnnotationType(annotation.annotationType(), Min.class)) {
            fieldRule = parseMin(typeElement, annotation);
        } else if (Utils.isAnnotationType(annotation.annotationType(), Max.class)) {
            fieldRule = parseMax(typeElement, annotation);
        } else if (Utils.isAnnotationType(annotation.annotationType(), Select.class)) {
            fieldRule = parseSpinner(typeElement, annotation);
        } else if (Utils.isAnnotationType(annotation.annotationType(), Range.class)) {
            fieldRule = parseRange(typeElement, annotation);
        } else if (Utils.isAnnotationType(annotation.annotationType(), CreditCard.class)) {
            fieldRule = parseCreditCard(typeElement, annotation);
        } else {
            fieldRule = parse(new FieldRule(), typeElement, annotation);
        }

        return fieldRule;
    }

    private<T extends Annotation> List<FieldRule> parseList(TypeElement typeElement, Class<T> annotationType) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KubwaException {
        List<FieldRule> fieldRules = new ArrayList<>();
        T annotationParent = typeElement.getAnnotation(annotationType);

        T[] annotationList = (T[]) annotationParent.annotationType().getMethod("value").invoke(annotationParent);

        for (T annotation : annotationList) {
            fieldRules.add(parseSingle(typeElement, annotation));
        }


        return fieldRules;
    }


    private Class<? extends Rule> getRuleType(Annotation ruleAnnotation) {
        ValidateUsing validateUsing = ruleAnnotation.annotationType().getAnnotation(ValidateUsing.class);
        return validateUsing != null ? validateUsing.value() : null;
    }

    private Rule createRule(Class<? extends Rule> ruleType, final Annotation ruleAnnotation) {
        Rule rule = null;

        try {
            if (Rule.class.isAssignableFrom(ruleType)) {
                Constructor<?> constructor;

                if (Utils.isAnnotationType(ruleAnnotation.annotationType(), Password.class)) {
                    constructor = ruleType.getDeclaredConstructor(PasswordRule.Scheme.class);
                    constructor.setAccessible(true);
                    rule = (Rule) constructor.newInstance(ruleAnnotation.annotationType().getMethod("scheme").invoke(ruleAnnotation));
                } else if (Utils.isAnnotationType(ruleAnnotation.annotationType(), Checked.class)) {
                    constructor = ruleType.getDeclaredConstructor(Boolean.class);
                    constructor.setAccessible(true);
                    rule = (Rule) constructor.newInstance(ruleAnnotation.annotationType().getMethod("value").invoke(ruleAnnotation));
                } else if (Utils.isAnnotationType(ruleAnnotation.annotationType(), Min.class)
                        || Utils.isAnnotationType(ruleAnnotation.annotationType(), Max.class)
                        || Utils.isAnnotationType(ruleAnnotation.annotationType(), Select.class)) {
                    constructor = ruleType.getDeclaredConstructor(Integer.class);
                    constructor.setAccessible(true);
                    rule = (Rule) constructor.newInstance(ruleAnnotation.annotationType().getMethod("value").invoke(ruleAnnotation));
                } else if (Utils.isAnnotationType(ruleAnnotation.annotationType(), Range.class)) {
                    constructor = ruleType.getDeclaredConstructor(Integer.class, Integer.class, Boolean.class);
                    constructor.setAccessible(true);
                    Integer min = (Integer) ruleAnnotation.annotationType().getMethod("min").invoke(ruleAnnotation);
                    Integer max = (Integer) ruleAnnotation.annotationType().getMethod("max").invoke(ruleAnnotation);
                    Boolean includeBounds = (Boolean) ruleAnnotation.annotationType().getMethod("includeBounds").invoke(ruleAnnotation);
                    rule = (Rule) constructor.newInstance(min, max, includeBounds);
                } else if (Utils.isAnnotationType(ruleAnnotation.annotationType(), CreditCard.class)) {
                    constructor = ruleType.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    rule = (CreditCardRule) constructor.newInstance();

                    CreditCardRule.Type[] creditCardTypes = (CreditCardRule.Type[]) ruleAnnotation.annotationType().getMethod("types").invoke(ruleAnnotation);
                    for (CreditCardRule.Type type : creditCardTypes) {
                        ((CreditCardRule) rule).addCreditCard(type);
                    }
                } else if (Utils.isAnnotationType(ruleAnnotation.annotationType(), Regex.class)) {
                    constructor = ruleType.getDeclaredConstructor(String.class);
                    constructor.setAccessible(true);
                    String regex = (String) ruleAnnotation.annotationType().getMethod("regex").invoke(ruleAnnotation);

                    rule = (RegexRule) constructor.newInstance(regex);
                } else {
                    constructor = ruleType.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    rule = (Rule) constructor.newInstance();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rule;
    }

    private void generate(Map<TypeElement, AnnotatedClass> annos) throws IOException {
        if (annos.size() == 0) {
            return;
        }

        for (AnnotatedClass annotatedClass : annos.values()) {
            String packageName = getPackageName(processingEnv.getElementUtils(), annotatedClass.typeElement);
            try {
                TypeSpec generatedClass = CodeGenerator.generateClass(annotatedClass);

                JavaFile javaFile = builder(packageName, generatedClass).build();
                javaFile.writeTo(processingEnv.getFiler());
            } catch (KubwaException e) {
                processingEnv.getMessager().printMessage(ERROR, e.getMessage(), annotatedClass.typeElement);
            }

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
