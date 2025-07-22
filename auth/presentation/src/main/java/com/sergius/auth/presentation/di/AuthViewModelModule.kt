package com.sergius.auth.presentation.di

import com.sergius.auth.presentation.login.LoginViewModel
import com.sergius.auth.presentation.signup.SignupViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignupViewModel)
}