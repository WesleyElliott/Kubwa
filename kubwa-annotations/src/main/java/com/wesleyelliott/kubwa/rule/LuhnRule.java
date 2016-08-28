package com.wesleyelliott.kubwa.rule;

import android.text.TextUtils;

/**
 * Created by wesley on 2016/08/04.
 */

public abstract class LuhnRule extends Rule<String> {

    private int modulus;

    public LuhnRule(int modulus) {
        super(String.class);
        this.modulus = modulus;
    }

    public boolean validate(String value) {
        return validate(value, ".+", true);
    }

    public boolean validate(String value, String regex) {
        return validate(value, regex, true);
    }

    public boolean validate(String value, String regex, boolean includesCheckDigit) {
        // Validate Regex
        if (TextUtils.isEmpty(value) || !value.matches(regex)) {
            return false;
        }

        int total = 0;
        for (int i = 0; i < value.length(); i++) {
            int lth = value.length() + (includesCheckDigit ? 0 : 1);
            int leftPos  = i + 1;
            int rightPos = lth - i;
            int charValue = toInt(value.charAt(i));
            total += weightedValue(charValue, leftPos, rightPos);
        }
        if (total == 0) {
            return false;
        }
        return (total % modulus) == 0;
    }

    private int weightedValue(int charValue, int leftPos, int rightPos) {
        int[] POSITION_WEIGHT = new int[] {2, 1};
        int weight = POSITION_WEIGHT[rightPos % 2];
        int weightedValue = charValue * weight;
        return weightedValue > 9 ? (weightedValue - 9) : weightedValue;
    }

    private int toInt(char character) {
        if (Character.isDigit(character)) {
            return Character.getNumericValue(character);
        }
        return -1;
    }

}
