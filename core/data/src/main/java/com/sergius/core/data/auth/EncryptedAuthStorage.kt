package com.sergius.core.data.auth

import androidx.datastore.core.DataStore
import com.sergius.core.data.mapper.toAuthInfo
import com.sergius.core.data.mapper.toUserPreferences
import com.sergius.core.domain.AuthInfo
import com.sergius.core.domain.SessionStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class EncryptedAuthStorage(
    private val dataStore: DataStore<UserPreferences>,
    private val dispatcher: CoroutineDispatcher,
) : SessionStorage {

    override suspend fun get(): AuthInfo? {
        return withContext(context = dispatcher) {
            dataStore.data.first().toAuthInfo()
        }
    }

    override suspend fun set(info: AuthInfo?) {
        withContext(context = dispatcher) {
            dataStore.updateData {
                info?.toUserPreferences() ?: UserPreferences()
            }
        }
    }
}