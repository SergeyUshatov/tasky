package com.sergius.domain

import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun signUp(name: String, email: String, password: String): EmptyResult<DataError.Network>
}