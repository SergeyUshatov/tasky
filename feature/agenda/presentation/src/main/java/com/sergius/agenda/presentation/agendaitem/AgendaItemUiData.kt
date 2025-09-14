package com.sergius.agenda.presentation.agendaitem

import com.sergius.core.domain.AgendaItemType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class AgendaItemUiData(
    val id: String = Uuid.random().toString(),
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
