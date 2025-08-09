package com.sergius.auth.presentation.signup

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.auth.domain.AuthRepository
import com.sergius.auth.domain.UserDataValidator
import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.onError
import com.sergius.core.domain.util.onSuccess
import com.sergius.core.presentation.ui.R
import com.sergius.core.presentation.ui.UiText
import com.sergius.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignupState())
    val state = _state
        .onStart {
            combine(
                snapshotFlow { _state.value.nameState.name.text },
                snapshotFlow { _state.value.emailState.email.text },
                snapshotFlow { _state.value.passwordState.password.text }
            ) { name, email, password ->
                val isEmailValid = userDataValidator.isValidEmail(email = email.toString())
                val passwordValidationState = userDataValidator.validatePassword(password = password.toString())
                _state.update {
                    it.copy(
                        emailState = _state.value.emailState.copy(isEmailValid = isEmailValid),
                        canSignup = name.isNotEmpty() && isEmailValid && passwordValidationState.isValidPassword
                    )
                }
            }.launchIn(viewModelScope)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SignupState()
        )

    private val eventChannel = Channel<SignupEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: SignUpScreenAction) {
        when (action) {
            is SignUpScreenAction.OnSignUpClick -> signup()
            is SignUpScreenAction.OnNameFocusChanged -> {
                _state.update {
                    it.copy(
                        nameState = _state.value.nameState.copy(isNameFocused = action.isFocused)
                    )
                }
            }

            is SignUpScreenAction.OnEmailFocusChanged -> {
                _state.update {
                    it.copy(
                        emailState = _state.value.emailState.copy(isEmailFocused = action.isFocused)
                    )
                }
            }

            is SignUpScreenAction.OnPasswordFocusChanged -> {
                _state.update {
                    it.copy(
                        passwordState = _state.value.passwordState.copy(isPasswordFocused = action.isFocused)
                    )
                }
            }

            is SignUpScreenAction.OnTogglePasswordVisibility -> {
                _state.update {
                    it.copy(
                        passwordState = _state.value.passwordState.copy(isPasswordVisible = !_state.value.passwordState.isPasswordVisible)
                    )
                }
            }

            else -> Unit
        }
    }

    private fun signup() {
        viewModelScope.launch {
            _state.update { it.copy(isSigningUp = true) }
            authRepository.signUp(
                name = _state.value.nameState.name.text.toString(),
                email = _state.value.emailState.email.text.toString().trim(),
                password = _state.value.passwordState.password.text.toString()
            ).onSuccess {
                eventChannel.send(SignupEvent.Success)
            }.onError { error ->
                when (error) {
                    DataError.Network.CONFLICT -> {
                        eventChannel.send(
                            SignupEvent.Error(
                                UiText.StringResource(R.string.error_email_exists)
                            )
                        )
                    }

                    else -> {
                        eventChannel.send(SignupEvent.Error(error.asUiText()))
                    }
                }
            }
            _state.update { it.copy(isSigningUp = false) }
        }
    }
}