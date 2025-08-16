package com.sergius.agenda.presentation.task

import androidx.compose.foundation.text.input.TextFieldState

sealed interface TaskDetailsAction {
    object OnCancelClick: TaskDetailsAction
    object OnSaveClick: TaskDetailsAction
    object OnEditTitleClick: TaskDetailsAction
    object OnEditDescriptionClick: TaskDetailsAction
    object OnDeleteClick: TaskDetailsAction
    object OnToggleTimerDialogVisibility: TaskDetailsAction
    object OnToggleDateDialogVisibility: TaskDetailsAction
    data class OnDateSelected(val date: Long?): TaskDetailsAction
    object OnToggleReminderDropdownVisibility: TaskDetailsAction
    data class OnDropdownItemClick(val item: String): TaskDetailsAction

    data class OnUpdateTitle(val titleSate: TextFieldState): TaskDetailsAction

    data class onResume(val title: String? = null): TaskDetailsAction
}