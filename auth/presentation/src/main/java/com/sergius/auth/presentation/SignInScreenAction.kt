package com.sergius.auth.presentation

sealed interface SignInScreenAction {
    data object OnLoginButtonClick : SignInScreenAction
    data object OnSignUpClick : SignInScreenAction
}