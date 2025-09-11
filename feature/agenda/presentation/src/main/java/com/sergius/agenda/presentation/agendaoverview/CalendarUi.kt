package com.sergius.agenda.presentation.agendaoverview

import java.time.Month

data class CalendarUi(
    val month: Month,
    val day: Int,
    val dayOfWeek: String
)
