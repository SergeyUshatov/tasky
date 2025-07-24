package com.sergius.core.data.auth

import androidx.datastore.core.Serializer
import com.sergius.core.data.Crypto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import kotlin.io.encoding.Base64

@Serializable
data class UserPreferences(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val userId: String? = null
)

object UserPreferencesSerializer : Serializer<UserPreferences> {
    override val defaultValue: UserPreferences
        get() = UserPreferences()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        val encryptedBase64 = withContext(Dispatchers.IO) {
            input.use { it.readBytes() }
        }

        val encrypted = Base64.decode(encryptedBase64)
        val decryptedBytes = Crypto.decrypt(encrypted)
        val decodedJsonString = decryptedBytes.decodeToString()
        return Json.decodeFromString(decodedJsonString)
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        val json = Json.encodeToString(t)
        val bytes = json.toByteArray()
        val encrypted = Crypto.encrypt(bytes)
        val encodedBase64 = Base64.encodeToByteArray(encrypted)

        withContext(Dispatchers.IO) {
            output.use {
                it.write(encodedBase64)
            }
        }
    }
}