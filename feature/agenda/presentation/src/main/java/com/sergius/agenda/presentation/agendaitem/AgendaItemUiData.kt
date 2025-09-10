package com.sergius.agenda.presentation.agendaitem

import com.sergius.core.domain.AgendaItemType

data class AgendaItemUiData(
    val id: String? = null,
    val title: String,
    val description: String,
    val remindAt: Long,
    val updatedAt: Long? = null,
    val time: Long,
    val from: Long,
    val to: Long,
    val isDone: Boolean = false,
    val itemType: AgendaItemType,
)
