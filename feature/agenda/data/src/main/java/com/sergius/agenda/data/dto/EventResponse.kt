package com.sergius.agenda.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    val event: EventDto,
    val uploadUrls: List<UploadUrlDto>
)
