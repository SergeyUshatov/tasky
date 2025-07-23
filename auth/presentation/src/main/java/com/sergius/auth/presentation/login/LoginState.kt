package com.sergius.auth.presentation.login

import com.sergius.auth.presentation.EmailFieldState
import com.sergius.auth.presentation.PasswordFieldState

data class LoginState(
    val emailState: EmailFieldState = EmailFieldState(),
    val passwordState: PasswordFieldState = PasswordFieldState(),
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false
)
