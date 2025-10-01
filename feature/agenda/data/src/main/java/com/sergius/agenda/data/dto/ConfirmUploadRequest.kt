package com.sergius.agenda.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmUploadRequest(
    val uploadedKeys: List<String>
)
