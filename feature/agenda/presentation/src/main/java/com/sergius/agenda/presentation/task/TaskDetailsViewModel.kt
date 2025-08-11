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
class TaskDetailsViewModel : ViewModel() {

    private val _state = MutableStateFlow(TaskDetailsState())
    val state = _state
        .onStart { }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = TaskDetailsState()
        )

    fun onAction(action: TaskDetailsAction) {
        when (action) {
            is TaskDetailsAction.OnToggleTimerDialogVisibility -> {
                _state.update {
                    it.copy(
                        showTimerDialog = !it.showTimerDialog
                    )
                }
            }

            is TaskDetailsAction.OnToggleDateDialogVisibility -> {
                _state.update {
                    it.copy(
                        showDateDialog = !it.showDateDialog
                    )
                }
            }

            is TaskDetailsAction.OnToggleReminderDropdownVisibility -> {
                _state.update {
                    it.copy(
                        showReminderDropdown = !it.showReminderDropdown
                    )
                }
            }

            is TaskDetailsAction.OnDropdownItemClick -> {
                _state.update {
                    it.copy(
                        reminderSelectedOption = action.item,
                        showReminderDropdown = false
                    )
                }
            }

            is TaskDetailsAction.OnEditTitleClick -> {

            }

            else -> Unit
        }
    }

    fun onAction(action: TaskTitleAction) {
        when (action) {
            is TaskTitleAction.OnTitleFocusChanged -> {
                _state.update {
                    it.copy(
                        isTitleFocused = action.isFocused
                    )
                }
            }

            is TaskTitleAction.OnCancelClick -> {
                _state.update {
                    it.taskTitle.undoState.undo()
                    it
                }
            }

            is TaskTitleAction.OnSaveClick -> {
                _state.update {
                    it.copy(
                        taskTitle = it.taskTitle
                    )
                }
            }
            else -> Unit
        }
    }

    private fun resetState() {
        _state.update {
            it.run { TaskDetailsState() }
        }
    }
}