package com.sergius.agenda.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: String,
    val title: String,
    val description: String,
    val from: String,
    val to: String,
    val hostId: String,
    val isUserEventCreator: Boolean,
    val attendees: List<AttendeeDto>,
    val photoKeys: List<PhotoDto>
)