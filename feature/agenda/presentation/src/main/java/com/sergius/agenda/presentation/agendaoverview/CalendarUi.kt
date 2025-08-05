package com.sergius.agenda.presentation.agendaoverview

import kotlinx.datetime.Month

data class CalendarUi(
    val month: Month,
    val day: Int,
    val dayOfWeek: String
)
