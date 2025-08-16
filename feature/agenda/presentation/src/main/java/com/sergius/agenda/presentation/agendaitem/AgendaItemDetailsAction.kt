package com.sergius.agenda.presentation.agendaitem

sealed interface AgendaItemDetailsAction {
    object OnCancelClick: AgendaItemDetailsAction
    object OnSaveClick: AgendaItemDetailsAction
    object OnEditTitleClick: AgendaItemDetailsAction
    object OnEditDescriptionClick: AgendaItemDetailsAction
    object OnDeleteClick: AgendaItemDetailsAction
    object OnToggleTimerDialogVisibility: AgendaItemDetailsAction
    object OnToggleDateDialogVisibility: AgendaItemDetailsAction
    data class OnDateSelected(val date: Long?): AgendaItemDetailsAction
    object OnToggleReminderDropdownVisibility: AgendaItemDetailsAction
    data class OnDropdownItemClick(val item: String): AgendaItemDetailsAction
}