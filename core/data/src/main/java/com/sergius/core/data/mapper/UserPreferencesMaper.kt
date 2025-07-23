package com.sergius.core.data.mapper

import com.sergius.core.data.auth.UserPreferences
import com.sergius.core.domain.AuthInfo

private const val UNDEFINED = "UNDEFINED"

fun UserPreferences.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken ?: UNDEFINED,
        refreshToken = refreshToken ?: UNDEFINED,
        userId = userId ?: UNDEFINED
    )
}

fun AuthInfo.toUserPreferences(): UserPreferences {
    return UserPreferences(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        userId = this.userId
    )
}