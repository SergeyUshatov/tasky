package com.sergius.tasky.di

import com.sergius.tasky.TaskyApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as TaskyApp).applicationScope
    }
}