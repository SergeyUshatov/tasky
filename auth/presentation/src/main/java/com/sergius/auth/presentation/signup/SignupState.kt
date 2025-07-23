package com.sergius.auth.presentation.signup

import com.sergius.auth.presentation.EmailFieldState
import com.sergius.auth.presentation.NameFieldState
import com.sergius.auth.presentation.PasswordFieldState

data class SignupState(
    val nameState: NameFieldState = NameFieldState(),
    val emailState: EmailFieldState = EmailFieldState(),
    val passwordState: PasswordFieldState = PasswordFieldState(),
    val canSignup: Boolean = false,
    val isSigningUp: Boolean = false
)
