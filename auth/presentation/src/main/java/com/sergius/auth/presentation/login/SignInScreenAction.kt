package com.sergius.auth.presentation.login

sealed interface SignInScreenAction {
    data object OnLoginClick: SignInScreenAction
    data object OnSignUpClick: SignInScreenAction
    data object OnTogglePasswordVisibility: SignInScreenAction
}