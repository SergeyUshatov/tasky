package com.sergius.core.domain.model

import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class Reminder(
    val id: String? = null,
    val title: String,
    val description: String,
    val remindAt: Long,
    val updatedAt: Long? = null,
    val time: Long
)
