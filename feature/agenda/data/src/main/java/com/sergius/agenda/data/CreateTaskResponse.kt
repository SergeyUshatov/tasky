package com.sergius.agenda.data

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskResponse(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val remindAt: String,
    val isDone: Boolean,
)
