package com.jeremiahzucker.pandroid.crypt

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * Created by jzucker on 6/30/17.
 * Blowfish encryption helpers (supports other crypto algorithms)
 * TODO: Remove enc and dec keys from default params
 */
class BlowFish(
    val encryptionKey: String = "6#26FRL\$ZWD",
    val decryptionKey: String = "R=U!LH\$O2B#",
    var algorithm: String = "Blowfish",
    var algorithmStrategy: String = "ECB",
    var paddingStrategy: String = "PKCS5Padding"
) {

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
}
