package com.sergius.agenda.presentation.task

sealed interface EditTextAction {
    object OnCancelClick : EditTextAction
    data class OnSaveClick(val text: String) : EditTextAction
}