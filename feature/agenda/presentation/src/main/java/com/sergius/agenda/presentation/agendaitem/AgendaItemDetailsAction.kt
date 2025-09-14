package com.sergius.agenda.presentation.agendaitem

import com.sergius.core.domain.AgendaItemType
import com.sergius.core.domain.model.PickerType

sealed interface AgendaItemDetailsAction {
    object OnCancelClick: AgendaItemDetailsAction
    data class OnSaveClick(val itemType: AgendaItemType): AgendaItemDetailsAction
    object OnEditTitleClick: AgendaItemDetailsAction
    object OnEditDescriptionClick: AgendaItemDetailsAction
    object OnDeleteClick: AgendaItemDetailsAction
    data class OnToggleTimerDialogVisibility(val pickerType: PickerType): AgendaItemDetailsAction
    data class OnToggleDateDialogVisibility(val pickerType: PickerType): AgendaItemDetailsAction
    data class OnDateSelected(val date: Long?): AgendaItemDetailsAction
    object OnToggleReminderDropdownVisibility: AgendaItemDetailsAction
    data class OnDropdownItemClick(val item: String): AgendaItemDetailsAction
}