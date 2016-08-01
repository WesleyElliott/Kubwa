package com.wesleyelliott.kubwa;

import android.content.Context;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.wesleyelliott.kubwa.fieldrule.CheckedFieldRule;
import com.wesleyelliott.kubwa.fieldrule.FieldRule;
import com.wesleyelliott.kubwa.fieldrule.MaxFieldRule;
import com.wesleyelliott.kubwa.fieldrule.MinFieldRule;
import com.wesleyelliott.kubwa.fieldrule.PasswordFieldRule;
import com.wesleyelliott.kubwa.fieldrule.RegexFieldRule;
import com.wesleyelliott.kubwa.rule.CheckedRule;
import com.wesleyelliott.kubwa.rule.ConfirmEmailRule;
import com.wesleyelliott.kubwa.rule.ConfirmPasswordRule;
import com.wesleyelliott.kubwa.rule.EmailRule;
import com.wesleyelliott.kubwa.rule.MaxRule;
import com.wesleyelliott.kubwa.rule.MinRule;
import com.wesleyelliott.kubwa.rule.PasswordRule;
import com.wesleyelliott.kubwa.rule.RegexRule;
import com.wesleyelliott.kubwa.rule.Rule;

import java.util.List;

import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * Created by wesley on 2016/07/28.
 */

public class CodeGenerator {

    public static TypeSpec generateClass(AnnotatedClass annotatedClass) throws KubwaException {
        String className = annotatedClass.annotatedClassName + "Validator";
        TypeSpec.Builder builder =  classBuilder(className)
                .addField(Context.class, "context")
                .addMethod(makeConstructor(annotatedClass.fieldRules))
                .addModifiers(PUBLIC, FINAL);


        for (FieldRule fieldRule : annotatedClass.fieldRules) {
            builder.addField(makeValidatorField(fieldRule));
            builder.addMethod(makeValidatorMethod(fieldRule));
            builder.addMethod(makeGetErrorMethod(fieldRule));
            builder.addMethod(makeSetErrorMethod(fieldRule));
        }

        builder.addMethod(makeIsValidMethod(annotatedClass.fieldRules));
        builder.addMethod(makeValidateAllMethod(annotatedClass.fieldRules));

        return builder.build();
    }

    private static FieldSpec makeValidatorField(FieldRule fieldRule) {
        return FieldSpec.builder(Validation.class, fieldRule.getFieldName())
                .addModifiers(PRIVATE)
                .build();
    }

    private static MethodSpec makeConstructor(List<FieldRule> fieldRuleList) throws KubwaException {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        builder.addModifiers(PUBLIC)
                .addParameter(Context.class, "context")
                .addStatement("this.$N = $N", "context", "context");

        for (FieldRule fieldRule : fieldRuleList) {
            Class<? extends Rule> fieldRuleType = fieldRule.fieldRuleType;
            if (Utils.isRuleType(fieldRuleType, PasswordRule.class)) {
                PasswordFieldRule passwordFieldRule = Utils.getRule(fieldRuleList, PasswordRule.class);
                builder.addStatement(passwordFieldRule.getFieldName() + " = new $T(context, $L, new $T($T.$L))", Validation.class, passwordFieldRule.fieldErrorResource, passwordFieldRule.fieldRuleType, passwordFieldRule.passwordScheme.getClass(), passwordFieldRule.passwordScheme);
            } else if (Utils.isRuleType(fieldRuleType, RegexRule.class)) {
                RegexFieldRule regexFieldRule = Utils.getRule(fieldRuleList, RegexRule.class);
                builder.addStatement(regexFieldRule.getFieldName() + " = new $T(context, $L, new $T($S))", Validation.class, regexFieldRule.fieldErrorResource, regexFieldRule.fieldRuleType, regexFieldRule.regex);
            } else if (Utils.isRuleType(fieldRuleType, CheckedRule.class)) {
                CheckedFieldRule checkedFieldRule = Utils.getRule(fieldRuleList, CheckedRule.class);
                builder.addStatement(checkedFieldRule.getFieldName() + " = new $T(context, $L, new $T($L))", Validation.class, checkedFieldRule.fieldErrorResource, checkedFieldRule.fieldRuleType, checkedFieldRule.checkedValue);
            } else if (Utils.isRuleType(fieldRuleType, ConfirmEmailRule.class)) {
                FieldRule emailFieldRule = Utils.getRule(fieldRuleList, EmailRule.class);
                if (emailFieldRule == null) {
                    throw new KubwaException("ConfirmEmailRule requires an EmailRule present!");
                }

                builder.addStatement(fieldRule.getFieldName() + " = new $T(context, $L, new $T())", Validation.class, fieldRule.fieldErrorResource, fieldRule.fieldRuleType);
            } else if (Utils.isRuleType(fieldRuleType, ConfirmPasswordRule.class)) {
                FieldRule passwordFieldRule = Utils.getRule(fieldRuleList, PasswordRule.class);
                if (passwordFieldRule == null) {
                    throw new KubwaException("ConfirmPasswordRule requires an PasswordRule present!");
                }

                builder.addStatement(fieldRule.getFieldName() + " = new $T(context, $L, new $T())", Validation.class, fieldRule.fieldErrorResource, fieldRule.fieldRuleType);
            } else if (Utils.isRuleType(fieldRuleType, MinRule.class)) {
                MinFieldRule checkedFieldRule = Utils.getRule(fieldRuleList, MinRule.class);
                builder.addStatement(checkedFieldRule.getFieldName() + " = new $T(context, $L, new $T($L))", Validation.class, checkedFieldRule.fieldErrorResource, checkedFieldRule.fieldRuleType, checkedFieldRule.minValue);
            } else if (Utils.isRuleType(fieldRuleType, MaxRule.class)) {
                MaxFieldRule checkedFieldRule = Utils.getRule(fieldRuleList, MaxRule.class);
                builder.addStatement(checkedFieldRule.getFieldName() + " = new $T(context, $L, new $T($L))", Validation.class, checkedFieldRule.fieldErrorResource, checkedFieldRule.fieldRuleType, checkedFieldRule.maxValue);
            } else {
                builder.addStatement(fieldRule.getFieldName() + " = new $T(context, $L, new $T())", Validation.class, fieldRule.fieldErrorResource, fieldRule.fieldRuleType);
            }
        }

        return builder.build();
    }

    private static MethodSpec.Builder makeValidatorStatement(MethodSpec.Builder builder, FieldRule fieldRule) {
        if (Utils.isRuleType(fieldRule.fieldRuleType, ConfirmEmailRule.class) || Utils.isRuleType(fieldRule.fieldRuleType, ConfirmPasswordRule.class)) {
            builder.addParameter(fieldRule.fieldRule.getType(), fieldRule.getValueName() + "1")
                    .addParameter(fieldRule.fieldRule.getType(), fieldRule.getValueName() + "2")
                    .addStatement("$L.validate($L, $L)", fieldRule.getFieldName(), fieldRule.getValueName() + "1", fieldRule.getValueName() + "2");
        } else {
            builder.addParameter(fieldRule.fieldRule.getType(), fieldRule.getValueName())
                    .addStatement("$L.validate($L)", fieldRule.getFieldName(), fieldRule.getValueName());
        }
        return builder;
    }

    private static MethodSpec makeValidatorMethod(FieldRule fieldRule) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(fieldRule.getMethodName())
                .addModifiers(PUBLIC);
        makeValidatorStatement(builder, fieldRule);
        return builder.build();
    }

    private static MethodSpec makeGetErrorMethod(FieldRule fieldRule) {
        return MethodSpec.methodBuilder(fieldRule.getErrorMessageMethodName())
                .addModifiers(PUBLIC)
                .addStatement("return $L.getMessage()", fieldRule.getFieldName())
                .returns(String.class)
                .build();
    }

    private static MethodSpec makeSetErrorMethod(FieldRule fieldRule) {
        return MethodSpec.methodBuilder(fieldRule.setErrorMessageMethodName())
                .addModifiers(PUBLIC)
                .addParameter(String.class, fieldRule.getValueName())
                .addStatement("$L.setMessage($L)", fieldRule.getFieldName(), fieldRule.getValueName())
                .build();
    }

    private static MethodSpec makeIsValidMethod(List<FieldRule> fieldRuleList) {
        MethodSpec.Builder isValidMethodSpec = MethodSpec.methodBuilder("isValid")
                .addModifiers(PUBLIC)
                .returns(TypeName.BOOLEAN);

        StringBuilder builder = new StringBuilder();
        builder.append("true");
        for (FieldRule fieldRule : fieldRuleList) {
            builder.append(" && ");
            builder.append(fieldRule.getErrorMessageMethodName());
            builder.append("()");
            builder.append(" == null ");
        }

        isValidMethodSpec.addStatement("return $L", builder.toString());

        return isValidMethodSpec.build();
    }

    private static MethodSpec makeValidateAllMethod(List<FieldRule> fieldRuleList) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("validateAll")
                .addModifiers(PUBLIC);

        for (FieldRule fieldRule : fieldRuleList) {
            if (Utils.isRuleType(fieldRule.fieldRuleType, ConfirmEmailRule.class)) {
                FieldRule emailFieldRule = Utils.getRule(fieldRuleList, EmailRule.class);
                builder.addParameter(fieldRule.fieldRule.getType(), fieldRule.getValueName());
                builder.addStatement("$L.validate($L, $L);", fieldRule.getFieldName(), fieldRule.getValueName(), emailFieldRule.getValueName());
            } else if(Utils.isRuleType(fieldRule.fieldRuleType, ConfirmPasswordRule.class)) {
                FieldRule passwordFieldRule = Utils.getRule(fieldRuleList, PasswordRule.class);
                builder.addParameter(fieldRule.fieldRule.getType(), fieldRule.getValueName());
                builder.addStatement("$L.validate($L, $L);", fieldRule.getFieldName(), fieldRule.getValueName(), passwordFieldRule.getValueName());
            } else {
                builder.addParameter(fieldRule.fieldRule.getType(), fieldRule.getValueName());
                builder.addStatement("$L.validate($L);", fieldRule.getFieldName(), fieldRule.getValueName());
            }
        }

        return builder.build();
    }
}
