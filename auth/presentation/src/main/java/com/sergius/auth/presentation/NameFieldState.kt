package com.sergius.auth.presentation

import androidx.compose.foundation.text.input.TextFieldState

data class NameFieldState(
    val name: TextFieldState = TextFieldState(),
    val isNameFocused: Boolean = false,
)
