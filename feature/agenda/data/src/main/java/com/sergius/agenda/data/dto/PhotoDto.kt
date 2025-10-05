package com.sergius.agenda.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    val key: String,
    val url: String,
)
