package com.sergius.agenda.presentation.agendaoverview

import com.sergius.agenda.presentation.agendaitem.AgendaItemUiData

data class AgendaState(
    val fabExpanded: Boolean = false,
    val month: String = "UNSPECIFIED",
    val monthDays: List<Int> = emptyList(),
    val days: List<CalendarUi> = emptyList(),
    val items: List<AgendaItemUiData> = emptyList(),
    val expandedItemId: String? = null
)


