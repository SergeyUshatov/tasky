package com.sergius.core.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Task(
    val id: String = Uuid.random().toString(),
    val title: String,
    val description: String,
    val remindAt: Long,
    val updatedAt: Long? = null,
    val time: Long,
    val isDone: Boolean = false,
)
