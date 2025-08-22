package com.sergius.agenda.presentation.agendaitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergius.agenda.data.EditTextState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class EditTextScreenViewModel(
    private val initialText: String
): ViewModel() {
    private val _state = MutableStateFlow(EditTextState())
    val state = _state
        .onStart {
            _state.update {
                it.textState.edit {
                    append(initialText)
                }
                it
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = EditTextState()
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
}