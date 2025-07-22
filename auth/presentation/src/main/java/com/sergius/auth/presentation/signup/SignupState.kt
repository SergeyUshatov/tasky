package com.sergius.auth.presentation.signup

import androidx.compose.foundation.text.input.TextFieldState

data class SignupState(
    val name: TextFieldState = TextFieldState(),
    val isNameFocused: Boolean = false,
    val email: TextFieldState = TextFieldState(),
    val isEmailFocused: Boolean = false,
    val isEmailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isPasswordFocused: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val canSignup: Boolean = false,
    val isSigningUp: Boolean = false
)
