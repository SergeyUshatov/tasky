package com.sergius.agenda.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val remindAt: String,
    val isDone: Boolean,
)
