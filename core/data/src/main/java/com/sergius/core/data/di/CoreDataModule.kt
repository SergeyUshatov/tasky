package com.sergius.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.sergius.core.data.auth.EncryptedAuthStorage
import com.sergius.core.data.auth.UserPreferences
import com.sergius.core.data.auth.UserPreferencesSerializer
import com.sergius.core.data.networking.HttpClientFactory
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val Context.sessionStore: DataStore<UserPreferences> by dataStore(
    fileName = "user_session_store",
    serializer = UserPreferencesSerializer
)

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }

    single {
        EncryptedAuthStorage(dataStore = androidContext().sessionStore, dispatcher = Dispatchers.IO)
    }
}