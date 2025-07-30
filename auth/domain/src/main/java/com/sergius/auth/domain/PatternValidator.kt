package com.sergius.auth.domain

fun interface PatternValidator {
    fun matches(value: String): Boolean
}