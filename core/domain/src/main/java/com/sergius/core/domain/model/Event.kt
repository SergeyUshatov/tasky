package com.sergius.core.domain.model

data class Event(
    val id: String? = null,
    val title: String,
    val description: String,
    val remindAt: Long,
    val updatedAt: Long? = null,
    val from: Long,
    val to: Long,
    val attendeeIds: List<String>,
    val photoKeys: List<String>
)
