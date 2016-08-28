package com.wesleyelliott.kubwa.rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 2016/08/04.
 */

public class CreditCardRule extends LuhnRule {

    private List<Type> creditCardTypes;

    public CreditCardRule() {
        super(10);
    }

    public CreditCardRule(CreditCardRule.Type... creditCardTypes) {
        super(10);
        if (this.creditCardTypes == null) {
            this.creditCardTypes = new ArrayList<>();
        }
        for (CreditCardRule.Type type : creditCardTypes) {
            this.creditCardTypes.add(type);
        }
    }

    public void addCreditCard(Type creditCardType) {
        if (creditCardTypes == null) {
            creditCardTypes = new ArrayList<>();
        }
        creditCardTypes.add(creditCardType);
    }

    @Override
    public boolean isValid(String value) {
        for (Type type : creditCardTypes) {
            if (validate(value, type.getRegex())) {
                return true;
            }
        }
        return false;
    }

    public enum Type {
        VISA("^(4)(\\d{12}|\\d{15})$"),
        MASTERCARD("^(5[1-5]\\d{14})$");

        private String regex;

        Type(String regex) {
            this.regex = regex;
        }

        public String getRegex() {
            return regex;
        }
    }
}
