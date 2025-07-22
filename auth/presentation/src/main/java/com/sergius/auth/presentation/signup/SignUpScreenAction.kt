package com.sergius.auth.presentation.signup

sealed interface SignUpScreenAction {
    data object OnLoginClick: SignUpScreenAction
    data object OnSignUpClick: SignUpScreenAction
    data object OnTogglePasswordVisibility: SignUpScreenAction
    data class OnNameFocusChanged(val isFocused: Boolean): SignUpScreenAction
    data class OnEmailFocusChanged(val isFocused: Boolean): SignUpScreenAction
    data class OnPasswordFocusChanged(val isFocused: Boolean): SignUpScreenAction
}