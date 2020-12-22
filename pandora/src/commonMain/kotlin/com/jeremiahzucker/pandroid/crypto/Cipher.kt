package com.jeremiahzucker.pandroid.crypto

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

// TODO: Platform specific
/** Simple [javax.crypto.Cipher] wrapper to easily encrypt and decrypt content */
class Cipher(val config: Config) {

    private val transformation get() = "${config.algorithm}/${config.algorithmStrategy}/${config.paddingStrategy}"

    fun encrypt(decrypted: String, key: String = config.encryptionKey) =
        encrypt(decrypted.toByteArray(), key)

    fun decrypt(encrypted: String, key: String = config.decryptionKey) =
        decrypt(encrypted.toByteArray(), key)

    fun encrypt(decrypted: ByteArray, key: String = config.encryptionKey) =
        transform(decrypted, key, Cipher.ENCRYPT_MODE)

    fun decrypt(encrypted: ByteArray, key: String = config.decryptionKey) =
        transform(encrypted, key, Cipher.DECRYPT_MODE)

    private fun transform(raw: ByteArray, key: String, mode: Int) = Cipher.getInstance(transformation).run {
        init(mode, generateSecret(key))
        doFinal(raw)
    }

    private fun generateSecret(key: String) = SecretKeySpec(
        key.toByteArray(Charsets.UTF_8),
        config.algorithm
    )

    /** Configuration for [Cipher], defaults to Pandora Blowfish algorithm */
    data class Config(
        var encryptionKey: String = "6#26FRL\$ZWD",
        var decryptionKey: String = "R=U!LH\$O2B#",
        var algorithm: String = "Blowfish",
        var algorithmStrategy: String = "ECB",
        var paddingStrategy: String = "PKCS5Padding",
    ) {

        fun build() = Cipher(this)

    }
}
