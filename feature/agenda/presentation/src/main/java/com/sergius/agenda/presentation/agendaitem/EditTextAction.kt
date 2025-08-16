package com.sergius.agenda.presentation.agendaitem

sealed interface EditTextAction {
    object OnCancelClick : EditTextAction
    data class OnSaveClick(val text: String) : EditTextAction
    data class OnFocusChanged(val isFocused: Boolean) : EditTextAction
}