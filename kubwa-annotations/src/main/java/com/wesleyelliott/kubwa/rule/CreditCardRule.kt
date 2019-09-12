package com.wesleyelliott.kubwa.rule

/**
 * Created by wesley on 2016/08/04.
 */

class CreditCardRule : LuhnRule {

    private val creditCardTypes = mutableListOf<Type>()

    constructor() : super(10)

    constructor(vararg creditCardTypes: Type) : super(10) {
        for (type in creditCardTypes) {
            this.creditCardTypes.add(type)
        }
    }

    fun addCreditCard(creditCardType: Type) {
        creditCardTypes.add(creditCardType)
    }

    override fun isValid(value: String?): Boolean {
        for (type in creditCardTypes) {
            if (value != null) {
                if (validate(value, type.regex)) {
                    return true
                }
            }
        }
        return false
    }

    enum class Type private constructor(val regex: String) {
        VISA("^(4)(\\d{12}|\\d{15})$"),
        MASTERCARD("^(5[1-5]\\d{14})$")
    }
}
