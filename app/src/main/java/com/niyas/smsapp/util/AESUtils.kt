package com.niyas.smsapp.util

import android.util.Base64
import java.security.SecureRandom
import java.util.Arrays
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtils {

    private const val AES_MODE = "AES/CBC/PKCS7Padding"
    const val IV_LENGTH = 16 // AES initialization vector length

    fun encrypt(text: String, key: String): String {
        val iv = ByteArray(IV_LENGTH)
        val random = SecureRandom()
        random.nextBytes(iv)

        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, getKeySpec(key), IvParameterSpec(iv))
        val encryptedBytes = cipher.doFinal(text.toByteArray())

        val combined = ByteArray(iv.size + encryptedBytes.size)
        System.arraycopy(iv, 0, combined, 0, iv.size)
        System.arraycopy(encryptedBytes, 0, combined, iv.size, encryptedBytes.size)

        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    fun decrypt(encryptedText: String, key: String): String {
        val combined = Base64.decode(encryptedText, Base64.DEFAULT)

        val iv = ByteArray(IV_LENGTH)
        System.arraycopy(combined, 0, iv, 0, IV_LENGTH)

        val encryptedBytes = ByteArray(combined.size - IV_LENGTH)
        System.arraycopy(combined, IV_LENGTH, encryptedBytes, 0, encryptedBytes.size)

        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.DECRYPT_MODE, getKeySpec(key), IvParameterSpec(iv))
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        return String(decryptedBytes)
    }

    private fun getKeySpec(key: String): SecretKeySpec {
        val keyBytes = ByteArray(32) // 256-bit key
        Arrays.fill(keyBytes, 0.toByte())
        val keyData = key.toByteArray(Charsets.UTF_8)
        System.arraycopy(keyData, 0, keyBytes, 0, Math.min(keyData.size, keyBytes.size))
        return SecretKeySpec(keyBytes, "AES")
    }
}