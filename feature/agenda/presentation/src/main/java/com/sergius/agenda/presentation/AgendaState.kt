package com.sergius.agenda.presentation

import kotlinx.datetime.Month

data class AgendaState(
    val fabExpanded: Boolean = false,
    val month: Month,
    val monthDays: List<Int> = emptyList(),
    val days: List<CalendarUi> = emptyList(),
)


