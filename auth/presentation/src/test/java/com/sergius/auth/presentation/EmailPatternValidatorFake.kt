package com.sergius.auth.presentation

import com.sergius.domain.PatternValidator

object EmailPatternValidatorFake: PatternValidator {

    private var emailMatches = false

    fun setEmailValidation(newValue: Boolean) {
        emailMatches = newValue
    }

    override fun matches(value: String): Boolean {
        return emailMatches
    }
}