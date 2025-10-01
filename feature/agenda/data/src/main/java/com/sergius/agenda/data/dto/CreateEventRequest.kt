package com.sergius.agenda.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateEventRequest(
    val id: String,
    val title: String,
    val description: String,
    val from: String,
    val to: String,
    val remindAt: String,
    val updatedAt: String,
    val attendeeIds: List<String>,
    val photoKeys: List<String>,
)