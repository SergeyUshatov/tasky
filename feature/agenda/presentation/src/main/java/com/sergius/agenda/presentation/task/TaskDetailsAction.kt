package com.sergius.agenda.presentation.task

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
}