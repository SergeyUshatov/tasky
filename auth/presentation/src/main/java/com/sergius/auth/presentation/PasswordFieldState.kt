package com.sergius.auth.presentation

import androidx.compose.foundation.text.input.TextFieldState

data class PasswordFieldState(
    val password: TextFieldState = TextFieldState(),
    val isPasswordFocused: Boolean = false,
    val isPasswordVisible: Boolean = false,
)
