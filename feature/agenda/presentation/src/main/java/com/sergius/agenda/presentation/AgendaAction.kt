package com.sergius.agenda.presentation

sealed interface AgendaAction {
    data class OnDayClick(val day: CalendarUi) : AgendaAction
}