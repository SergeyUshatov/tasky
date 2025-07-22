package com.sergius.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun onAction(action: SignInScreenAction) {
        when(action) {
            is SignInScreenAction.OnLoginClick -> login()
            is SignInScreenAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            is SignInScreenAction.OnEmailFocusChanged -> {
                state = state.copy(
                    isEmailFocused = action.isFocused
                )
            }
            is SignInScreenAction.OnPasswordFocusChanged -> {
                state = state.copy(
                    isPasswordFocused = action.isFocused
                )
            }
            else -> Unit
        }
    }

    private fun login() {
        TODO("Not yet implemented")
    }
}