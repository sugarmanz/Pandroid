package com.jz.pandroid.request.crypt

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * Created by jzucker on 6/30/17.
 * Blowfish encryption helpers
 * TODO: Remove enc and dec keys from default params
 */
class BlowFish(val encryptionKey: String = "6#26FRL\$ZWD",
               val decryptionKey: String = "R=U!LH\$O2B#",
               val algorithm: String = "Blowfish",
               val algorithmStrategy: String = "ECB",
               val paddingStrategy: String = "PKCS5Padding") {

    val transformation = "$algorithm/$algorithmStrategy/$paddingStrategy"

    fun encrypt(decrypted: String, key: String = encryptionKey): ByteArray {
        return blow(decrypted, key, Cipher.ENCRYPT_MODE)
    }

    fun decrypt(encrypted: String, key: String = decryptionKey): ByteArray {
        return blow(encrypted, key, Cipher.DECRYPT_MODE)
    }

    fun encrypt(decrypted: ByteArray, key: String = encryptionKey): ByteArray {
        return blow(decrypted, key, Cipher.ENCRYPT_MODE)
    }

    fun decrypt(encrypted: ByteArray, key: String = decryptionKey): ByteArray {
        return blow(encrypted, key, Cipher.DECRYPT_MODE)
    }

    private fun tetrodotoxin(key: String, algorithm: String = "Blowfish"): SecretKeySpec {
        val keyData = key.toByteArray(Charsets.UTF_8)
        return SecretKeySpec(keyData, algorithm)
    }

    private fun blow(untouched: String, key: String, mode: Int): ByteArray {
        return blow(untouched.toByteArray(Charsets.UTF_8), key, mode)
    }

    private fun blow(untouched: ByteArray, key: String, mode: Int): ByteArray {
        // create a cipher based upon Blowfish
        val cipher = Cipher.getInstance(transformation)

        // initialise cipher to with secret key
        cipher.init(mode, tetrodotoxin(key))

        // encrypt message
        return cipher.doFinal(untouched)
    }

    // TODO: Remove conversion helpers
    fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

    fun bytesToHex(array: ByteArray): String {
        val builder = StringBuilder()
        for (b in array) {
            builder.append(String.format("%02x", b))
        }
        return builder.toString()
    }
}