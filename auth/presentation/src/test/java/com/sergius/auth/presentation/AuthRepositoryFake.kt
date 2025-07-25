package com.sergius.auth.presentation

import com.sergius.core.domain.util.DataError
import com.sergius.core.domain.util.EmptyResult
import com.sergius.domain.AuthRepository
import com.sergius.core.domain.util.Result

class AuthRepositoryFake: AuthRepository {

    private var loginResult: Result<Unit, DataError.Network> = Result.Success(Unit)
    private var signUpResult: Result<Unit, DataError.Network> = Result.Success(Unit)

    fun setLoginResult(result: Result<Unit, DataError.Network>) {
        this.loginResult = result
    }

    fun setSignUpResult(result: Result<Unit, DataError.Network>) {
        this.signUpResult = result
    }

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