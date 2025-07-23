package com.sergius.auth.data.di

import com.sergius.auth.data.EmailPatternValidator
import com.sergius.domain.PatternValidator
import com.sergius.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> { EmailPatternValidator }
    singleOf(::UserDataValidator)
}