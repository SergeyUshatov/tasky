package com.sergius.auth.presentation.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.onError
import com.sergius.core.domain.util.onSuccess
import com.sergius.core.presentation.ui.R
import com.sergius.core.presentation.ui.UiText
import com.sergius.core.presentation.ui.asUiText
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
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            combine(
                snapshotFlow { _state.value.emailState.email.text },
                snapshotFlow { _state.value.passwordState.password.text }
            ) { email, password ->
                val isEmailValid = userDataValidator.isValidEmail(email = email.toString())
                _state.value = _state.value.copy(
                    emailState = _state.value.emailState.copy(isEmailValid = isEmailValid),
                    canLogin = isEmailValid && password.isNotEmpty()
                )
            }.launchIn(viewModelScope)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = LoginState()
        )

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: SignInScreenAction) {
        when (action) {
            is SignInScreenAction.OnLoginClick -> login()
            is SignInScreenAction.OnTogglePasswordVisibility -> {
                _state.value = _state.value.copy(
                    passwordState = _state.value.passwordState.copy(
                        isPasswordVisible = !_state.value.passwordState.isPasswordVisible
                    )
                )
            }

            is SignInScreenAction.OnEmailFocusChanged -> {
                _state.value = _state.value.copy(
                    emailState = _state.value.emailState.copy(isEmailFocused = action.isFocused)
                )
            }

            is SignInScreenAction.OnPasswordFocusChanged -> {
                _state.value = _state.value.copy(
                    passwordState = _state.value.passwordState.copy(isPasswordFocused = action.isFocused)
                )
            }

            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoggingIn = true)
            authRepository.login(
                email = _state.value.emailState.email.text.toString().trim(),
                password = _state.value.passwordState.password.text.toString()
            ).onSuccess {
                eventChannel.send(LoginEvent.Success)
            }.onError { error ->
                when(error) {
                    DataError.Network.UNAUTHORIZED -> {
                        eventChannel.send(
                            LoginEvent.Error(
                                UiText.StringResource(R.string.error_email_or_password_incorrect)
                            )
                        )
                    }
                    else -> {
                        eventChannel.send(LoginEvent.Error(error.asUiText()))
                    }
                }
            }
            _state.value = _state.value.copy(isLoggingIn = false)
        }
    }
}