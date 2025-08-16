package com.sergius.agenda.presentation

import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.agenda.presentation.task.EditTextAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class EditTextScreenViewModel(
    private val initialText: String
): ViewModel() {
    private val _state = MutableStateFlow(EditTextState(initialText = initialText))
    val state = _state
        .onStart { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = EditTextState(initialText = initialText)
        )

    fun onAction(action: EditTextAction) {
        when(action) {
            is EditTextAction.OnFocusChanged -> {
                _state.update {
                    it.copy(
                        isFocused = action.isFocused
                    )
                }
            }

            else -> Unit
        }
    }

    fun updateText(newText: String) {
        _state.update {
            it.textState.clearText()
            it.textState.edit {
                append(newText)
            }
            it
        }
    }
}