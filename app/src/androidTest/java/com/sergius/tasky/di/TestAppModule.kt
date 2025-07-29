package com.sergius.tasky.di

import com.sergius.tasky.KoinTestApplication
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val testAppModule = module {
    single<CoroutineScope> {
        (androidApplication() as KoinTestApplication).applicationScope
    }
}