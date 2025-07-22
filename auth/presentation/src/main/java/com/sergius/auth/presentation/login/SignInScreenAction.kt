package com.sergius.auth.presentation.login

sealed interface SignInScreenAction {
    data object OnLoginClick: SignInScreenAction
    data object OnSignUpClick: SignInScreenAction
    data object OnTogglePasswordVisibility: SignInScreenAction
    data class OnEmailFocusChanged(val isFocused: Boolean): SignInScreenAction
    data class OnPasswordFocusChanged(val isFocused: Boolean): SignInScreenAction
}