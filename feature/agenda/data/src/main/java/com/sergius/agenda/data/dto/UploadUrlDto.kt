package com.sergius.agenda.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UploadUrlDto(
    val photoKey: String,
    val uploadKey: String,
    val url: String,
)
