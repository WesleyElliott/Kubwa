package com.wesleyelliott.kubwa;

import android.content.Context;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * Created by wesley on 2016/07/28.
 */

public class CodeGenerator {

    public static TypeSpec generateClass(AnnotatedClass annotatedClass) {
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

    private static MethodSpec makeConstructor(List<FieldRule> fieldRuleList) {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        builder.addModifiers(PUBLIC)
                .addParameter(Context.class, "context")
                .addStatement("this.$N = $N", "context", "context");

        for (FieldRule fieldRule : fieldRuleList) {

            switch (fieldRule.fieldRule.getSimpleName()) {
                case "PasswordRule":
                    builder.addStatement(fieldRule.getFieldName() + " = new $T(context, $L, new $T($T.$L))", Validation.class, fieldRule.fieldErrorResource, fieldRule.fieldRule, fieldRule.passwordScheme.getClass(), fieldRule.passwordScheme);
                    break;
                case "RegexRule":
                    builder.addStatement(fieldRule.getFieldName() + " = new $T(context, $L, new $T($S))", Validation.class, fieldRule.fieldErrorResource, fieldRule.fieldRule, fieldRule.regex);
                    break;
                default:
                    builder.addStatement(fieldRule.getFieldName() + " = new $T(context, $L, new $T())", Validation.class, fieldRule.fieldErrorResource, fieldRule.fieldRule);
                    break;
            }

        }

        return builder.build();
    }

    private static MethodSpec makeValidatorMethod(FieldRule fieldRule) {
        return methodBuilder(fieldRule.getMethodName())
                .addModifiers(PUBLIC)
                .addParameter(String.class, "value")
                .addStatement("$L.validate(value);", fieldRule.getFieldName())
                .build();
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
                .addParameter(String.class, "value")
                .addStatement("$L.setMessage($L)", fieldRule.getFieldName(), "value")
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
            builder.addParameter(String.class, fieldRule.getValueName());
            builder.addStatement("$L.validate($L);", fieldRule.getFieldName(), fieldRule.getValueName());
        }

        return builder.build();
    }
}
