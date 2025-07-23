package com.sergius.auth.presentation.signup

import com.sergius.core.presentation.ui.UiText

interface SignupEvent {
    data class Error(val error: UiText): SignupEvent
    data object Success: SignupEvent
}