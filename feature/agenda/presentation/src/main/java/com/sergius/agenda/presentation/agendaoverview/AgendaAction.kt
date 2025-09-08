package com.sergius.agenda.presentation.agendaoverview

import com.sergius.agenda.presentation.agendaitem.AgendaItemUiData

sealed interface AgendaAction {
    data class OnDayClick(val day: CalendarUi) : AgendaAction
    object OnCreateAgendaItemClick: AgendaAction
    object OnTaskCreateClick: AgendaAction
    object OnEventCreateClick: AgendaAction
    object OnReminderCreateClick: AgendaAction
    object OnToggleMoreActionsDropdownVisibility: AgendaAction
    data class OnOpenItem(val item: AgendaItemUiData): AgendaAction
    data class OnEditItem(val item: AgendaItemUiData): AgendaAction
    data class OnDeleteItem(val item: AgendaItemUiData): AgendaAction
}