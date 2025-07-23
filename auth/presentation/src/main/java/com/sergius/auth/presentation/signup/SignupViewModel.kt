package com.sergius.auth.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.domain.AuthRepository
import com.sergius.domain.UserDataValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class SignupViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(SignupState())
        private set

    private val eventChannel = Channel<SignupEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _isComposing = MutableStateFlow(false)
    val isComposing = _isComposing
        .onStart {
            combine(
                snapshotFlow { state.nameState.name.text },
                snapshotFlow { state.emailState.email.text },
                snapshotFlow { state.passwordState.password.text }
            ) { name, email, password ->
                val isEmailValid = userDataValidator.isValidEmail(email = email.toString())
                val passwordValidationState = userDataValidator.validatePassword(password = password.toString())
                state = state.copy(
                    emailState = state.emailState.copy(isEmailValid = isEmailValid),
                    canSignup = name.isNotEmpty() && isEmailValid && passwordValidationState.isValidPassword
                )
            }.launchIn(viewModelScope)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = false
        )

    fun onAction(action: SignUpScreenAction) {
        when (action) {
            is SignUpScreenAction.OnSignUpClick -> signup()
            is SignUpScreenAction.OnNameFocusChanged -> {
                state = state.copy(
                    nameState = state.nameState.copy(isNameFocused = action.isFocused)
                )
            }

            is SignUpScreenAction.OnEmailFocusChanged -> {
                state = state.copy(
                    emailState = state.emailState.copy(isEmailFocused = action.isFocused)
                )
            }

            is SignUpScreenAction.OnPasswordFocusChanged -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(isPasswordFocused = action.isFocused)
                )
            }

            is SignUpScreenAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(isPasswordVisible = !state.passwordState.isPasswordVisible)
                )
            }

            else -> Unit
        }
    }

    private fun signup() {
        TODO("Not yet implemented")
    }
}