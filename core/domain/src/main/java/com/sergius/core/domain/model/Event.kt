package com.sergius.core.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Event(
    val id: String =  Uuid.random().toString(),
    val title: String,
    val description: String,
    val remindAt: Long,
    val updatedAt: Long? = null,
    val from: Long,
    val to: Long,
    val attendeeIds: List<String>,
    val photoKeys: List<String>
)
