package com.sergius.auth.data

import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult
import com.sergius.domain.AuthRepository

class AuthRepositoryImpl: AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        TODO("Not yet implemented")
    }
}