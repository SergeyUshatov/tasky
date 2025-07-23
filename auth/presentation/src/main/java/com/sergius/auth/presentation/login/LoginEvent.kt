package com.sergius.auth.presentation.login

import com.sergius.core.presentation.ui.UiText

sealed interface LoginEvent {
    data class Error(val error: UiText): LoginEvent
    data object Success: LoginEvent
}