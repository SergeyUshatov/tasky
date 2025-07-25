package com.sergius.core.data

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

private const val KEY_ALIAS = "secret"
private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"

object Crypto {
    private val cipher = Cipher.getInstance(TRANSFORMATION)
    private val keyStore = KeyStore
        .getInstance("AndroidKeyStore")
        .apply {
            load(null)
        }

    fun encrypt(data: ByteArray): ByteArray {
        val key = getKey()
        val iv = cipher.iv
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return iv + cipher.doFinal(data)
    }

    fun decrypt(bytes: ByteArray): ByteArray {
        val key = getKey()
        val iv = bytes.copyOfRange(0, cipher.blockSize)
        val data = bytes.copyOfRange(cipher.blockSize, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        return cipher.doFinal(data)
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey {
        return KeyGenerator
            .getInstance(ALGORITHM)
            .apply {
                init(
                    KeyGenParameterSpec.Builder(
                        KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(PADDING)
                        .setRandomizedEncryptionRequired(true)
                        .setUserAuthenticationRequired(false)
                        .build()
                )
            }
            .generateKey()
    }
}