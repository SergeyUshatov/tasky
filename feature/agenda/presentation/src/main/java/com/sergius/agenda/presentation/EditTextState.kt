package com.sergius.agenda.presentation

import androidx.compose.foundation.text.input.TextFieldState

data class EditTextState(
    val initialText: String,
    val textState: TextFieldState = TextFieldState(initialText = initialText),
    val isFocused: Boolean = false
)
