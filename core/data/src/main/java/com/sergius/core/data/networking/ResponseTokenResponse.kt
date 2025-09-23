package com.sergius.core.data.networking

import kotlinx.serialization.Serializable

@Serializable
data class ResponseTokenResponse(
    val accessToken: String,
    val refreshToken: String
)
