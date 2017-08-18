package com.jz.pandroid.request.crypt

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * Created by jzucker on 6/30/17.
 * Blowfish encryption helpers (supports other crypto algorithms)
 * TODO: Remove enc and dec keys from default params
 */
class BlowFish(val encryptionKey: String = "6#26FRL\$ZWD",
               val decryptionKey: String = "R=U!LH\$O2B#",
               var algorithm: String = "Blowfish",
               var algorithmStrategy: String = "ECB",
               var paddingStrategy: String = "PKCS5Padding") {

    // This way the changes to the algorithm stats get picked up upon decryption
    val transformation get() = "$algorithm/$algorithmStrategy/$paddingStrategy"

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

    private fun tetrodotoxin(key: String): SecretKeySpec {
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

    // Credit where credit is due
    // https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    private val hexArray = "0123456789abcdef".toCharArray()
    fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (j in bytes.indices) {
            val v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v.ushr(4)]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }
}