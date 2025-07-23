package com.sergius.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.domain.UserDataValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class LoginViewModel(
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _isComposing = MutableStateFlow(false)
    val isComposing = _isComposing
        .onStart {
            combine(
                snapshotFlow { state.emailState.email.text },
                snapshotFlow { state.passwordState.password.text }
            ) { email, password ->
                val isEmailValid = userDataValidator.isValidEmail(email = email.toString())
                state = state.copy(
                    emailState = state.emailState.copy(isEmailValid = isEmailValid),
                    canLogin = isEmailValid && password.isNotEmpty()
                )
            }.launchIn(viewModelScope)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = false
        )

    fun onAction(action: SignInScreenAction) {
        when (action) {
            is SignInScreenAction.OnLoginClick -> login()
            is SignInScreenAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(isPasswordVisible = !state.passwordState.isPasswordVisible)
                )
            }

            is SignInScreenAction.OnEmailFocusChanged -> {
                state = state.copy(
                    emailState = state.emailState.copy(isEmailFocused = action.isFocused)
                )
            }

            is SignInScreenAction.OnPasswordFocusChanged -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(isPasswordFocused = action.isFocused)
                )
            }

            else -> Unit
        }
    }

    private fun login() {
        TODO("Not yet implemented")
    }
}