package com.sergius.agenda.presentation.task

sealed interface EditTextAction {
    data class OnFieldFocusChanged(val isFocused: Boolean) : EditTextAction

    object OnCancelClick : EditTextAction
    object OnSaveClick : EditTextAction
}