package com.sergius.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTimestamp: String,
    val userName: String,
    val userId: String
)
