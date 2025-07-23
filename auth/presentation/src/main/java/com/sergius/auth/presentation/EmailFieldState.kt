package com.sergius.auth.presentation

import androidx.compose.foundation.text.input.TextFieldState

data class EmailFieldState(
    val email: TextFieldState = TextFieldState(),
    val isEmailFocused: Boolean = false,
    val isEmailValid: Boolean = false,
)
