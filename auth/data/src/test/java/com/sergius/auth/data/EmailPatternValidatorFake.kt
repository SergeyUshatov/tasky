package com.sergius.auth.data

import com.sergius.auth.domain.PatternValidator

object EmailPatternValidatorFake: PatternValidator {
    var emailMatches = false
    override fun matches(value: String): Boolean {
        return emailMatches
    }
}