package com.sergius.auth.presentation

import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult
import com.sergius.core.domain.util.Result
import com.sergius.auth.domain.AuthRepository

class AuthRepositoryFake: AuthRepository {

    var loginResult: Result<Unit, DataError.Network> = Result.Success(Unit)
    var signUpResult: Result<Unit, DataError.Network> = Result.Success(Unit)

    override suspend fun login(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return loginResult
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return signUpResult
    }
}