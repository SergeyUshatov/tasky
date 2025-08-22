package com.sergius.core.domain.model

data class Task(
    val id: String? = null,
    val title: String,
    val description: String,
    val remindAt: Long,
    val updatedAt: Long? = null,
    val time: Long,
    val isDone: Boolean = false,
)
