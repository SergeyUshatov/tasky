package com.sergius.agenda.presentation.task

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalMaterial3Api::class)
class TaskDetailsViewModel: ViewModel() {

    private val _state = MutableStateFlow(TaskDetailsState())
    val state = _state
        .onStart {  }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(2000),
            initialValue = TaskDetailsState()
        )

    fun onAction(action: TaskDetailsAction) {
        when(action) {
            is TaskDetailsAction.OnToggleTimerDialogVisibility -> {
                _state.value = _state.value.copy(showTimerDialog = !_state.value.showTimerDialog)
            }

            is TaskDetailsAction.OnToggleDateDialogVisibility -> {
                _state.value = _state.value.copy(showDateDialog = !_state.value.showDateDialog)
            }

            is TaskDetailsAction.OnToggleReminderDropdownVisibility -> {
                _state.value = _state.value.copy(showReminderDropdown = !_state.value.showReminderDropdown)
            }

            is TaskDetailsAction.OnDropdownItemClick -> {
                _state.value = _state.value.copy(
                    reminderSelectedOption = action.item,
                    showReminderDropdown = false
                )
            }

            else -> Unit
        }
    }
}