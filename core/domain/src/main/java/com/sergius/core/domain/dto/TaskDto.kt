package com.sergius.core.domain.dto

data class TaskDto(
    val id: String? = null,
    val title: String,
    val description: String,
    val remindAt: Long,
    val updatedAt: Long? = null,
    val time: Long,
    val isDone: Boolean = false,
)