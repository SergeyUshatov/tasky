package com.sergius.agenda.presentation.agendaoverview

data class AgendaState(
    val fabExpanded: Boolean = false,
    val month: String = "UNSPECIFIED",
    val monthDays: List<Int> = emptyList(),
    val days: List<CalendarUi> = emptyList(),
)


