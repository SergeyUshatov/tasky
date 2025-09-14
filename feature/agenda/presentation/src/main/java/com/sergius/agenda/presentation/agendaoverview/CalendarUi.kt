package com.sergius.agenda.presentation.agendaoverview

import java.time.Month
import java.time.Year

data class CalendarUi(
    val year: Int = Year.now().value,
    val month: Month,
    val day: Int,
    val dayOfWeek: String,
    val isSelected: Boolean = false
)
