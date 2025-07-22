package com.sergius.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.domain.UserDataValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow

class LoginViewModel(
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(
            snapshotFlow { state.email.text },
            snapshotFlow { state.password.text }
        ) { email, password ->
            val isEmailValid = userDataValidator.isValidEmail(email = email.toString())
            state = state.copy(
                isEmailValid = isEmailValid,
                canLogin = isEmailValid && password.isNotEmpty()
            )

        }.launchIn(viewModelScope)
    }

    fun onAction(action: SignInScreenAction) {
        when (action) {
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