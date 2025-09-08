package com.sergius.agenda.data

import androidx.compose.foundation.text.input.TextFieldState

data class EditTextState(
    val textState: TextFieldState = TextFieldState(),
    val isFocused: Boolean = false
)