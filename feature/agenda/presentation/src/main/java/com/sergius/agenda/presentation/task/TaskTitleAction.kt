package com.sergius.agenda.presentation.task

sealed interface TaskTitleAction {
    data class OnTitleFocusChanged(val isFocused: Boolean) : TaskTitleAction

    object OnCancelClick : TaskTitleAction
    object OnSaveClick : TaskTitleAction
}