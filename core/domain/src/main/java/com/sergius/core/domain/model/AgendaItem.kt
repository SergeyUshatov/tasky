package com.sergius.core.domain.model

import com.sergius.core.domain.AgendaItemType

data class AgendaItem(
    val id: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val updatedAt: Long? = null,
    val time: Long,
    val isDone: Boolean = false,
    val from: Long = 0L,
    val to: Long = 0L,
    val attendeeIds: List<String> = emptyList(),
    val photoKeys: List<String> = emptyList(),
    val itemType: AgendaItemType
)
