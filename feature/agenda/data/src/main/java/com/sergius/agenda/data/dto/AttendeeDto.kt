package com.sergius.agenda.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AttendeeDto(
    val email: String,
    val username: String,
    val userId: String,
    val eventId: String,
    val isGoing: Boolean,
    val remindAt: String,
)
