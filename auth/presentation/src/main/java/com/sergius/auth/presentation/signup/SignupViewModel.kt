package com.sergius.auth.presentation.signup

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

class SignupViewModel(
    private val userDataValidator: UserDataValidator
): ViewModel() {

    var state by mutableStateOf(SignupState())
        private set

    private val eventChannel = Channel<SignupEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(
            snapshotFlow { state.name.text },
            snapshotFlow { state.email.text },
            snapshotFlow { state.password.text }
        ) { name, email, password ->
            val isEmailValid = userDataValidator.isValidEmail(email = email.toString())
            val passwordState = userDataValidator.validatePassword(password = password.toString())
            state = state.copy(
                isEmailValid = isEmailValid,
                canSignup = name.isNotEmpty() && isEmailValid && passwordState.isValidPassword
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: SignUpScreenAction) {
        when (action) {
            is SignUpScreenAction.OnSignUpClick -> signup()
            is SignUpScreenAction.OnNameFocusChanged -> {
                state = state.copy(
                    isNameFocused = action.isFocused
                )
            }
            is SignUpScreenAction.OnEmailFocusChanged -> {
                state = state.copy(
                    isEmailFocused = action.isFocused
                )
            }
            is SignUpScreenAction.OnPasswordFocusChanged -> {
                state = state.copy(
                    isPasswordFocused = action.isFocused
                )
            }
            is SignUpScreenAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            else -> Unit
        }
    }

    private fun signup() {
        TODO("Not yet implemented")
    }
}