package com.sergius.domain

fun interface PatternValidator {
    fun matches(value: String): Boolean
}