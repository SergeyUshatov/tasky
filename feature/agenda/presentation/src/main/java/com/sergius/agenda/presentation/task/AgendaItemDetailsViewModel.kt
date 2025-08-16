package com.sergius.agenda.presentation.task

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
class AgendaItemDetailsViewModel : ViewModel() {

    private val _state = MutableStateFlow(AgendaItemDetailsState())
    val state = _state
        .onStart { }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = AgendaItemDetailsState()
        )

    fun onAction(action: AgendaItemDetailsAction) {
        when (action) {
            is AgendaItemDetailsAction.OnToggleTimerDialogVisibility -> {
                _state.update {
                    it.copy(
                        showTimerDialog = !it.showTimerDialog
                    )
                }
            }

            is AgendaItemDetailsAction.OnToggleDateDialogVisibility -> {
                _state.update {
                    it.copy(
                        showDateDialog = !it.showDateDialog
                    )
                }
            }

            is AgendaItemDetailsAction.OnToggleReminderDropdownVisibility -> {
                _state.update {
                    it.copy(
                        showReminderDropdown = !it.showReminderDropdown
                    )
                }
            }

            is AgendaItemDetailsAction.OnDropdownItemClick -> {
                _state.update {
                    it.copy(
                        reminderSelectedOption = action.item,
                        showReminderDropdown = false
                    )
                }
            }

            else -> Unit
        }
    }

    private fun resetState() {
        _state.update {
            it.run { AgendaItemDetailsState() }
        }
    }

    fun updateTitle(newTitle: String) {
        _state.update {
            it.copy(
                title = newTitle
            )
        }
    }

    fun updateDescription(description: String) {
        _state.update {
            it.copy(
                description = description
            )
        }
    }
}