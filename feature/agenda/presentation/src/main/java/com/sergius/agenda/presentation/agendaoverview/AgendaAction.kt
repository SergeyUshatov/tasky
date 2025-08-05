package com.sergius.agenda.presentation.agendaoverview

sealed interface AgendaAction {
    data class OnDayClick(val day: CalendarUi) : AgendaAction
    object OnCreateAgendaItemClick: AgendaAction
    object OnTaskCreateClick: AgendaAction
    object OnEventCreateClick: AgendaAction
    object OnReminderCreateClick: AgendaAction
}