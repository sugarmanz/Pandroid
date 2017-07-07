package com.jz.pandroid.request.crypt

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * Created by jzucker on 6/30/17.
 * Blowfish encryption helpers
 */

val EKEY = "6#26FRL\$ZWD"
val DKEY = "R=U!LH\$O2B#"
val TAG = "BlowFish"

fun encrypt(decrypted: String, key: String = EKEY): ByteArray {
    return blow(decrypted, key, Cipher.ENCRYPT_MODE)
}

fun decrypt(encrypted: String, key: String = DKEY): ByteArray {
    return blow(encrypted, key, Cipher.DECRYPT_MODE)
}

fun encrypt(decrypted: ByteArray, key: String = EKEY): ByteArray {
    return blow(decrypted, key, Cipher.ENCRYPT_MODE)
}

fun decrypt(encrypted: ByteArray, key: String = DKEY): ByteArray {
    return blow(encrypted, key, Cipher.DECRYPT_MODE)
}

fun tetrodotoxin(key: String, algorithm: String = "Blowfish"): SecretKeySpec {
    val keyData = key.toByteArray(Charsets.UTF_8)
    return SecretKeySpec(keyData, algorithm)
}

fun blow(untouched: String, key: String, mode: Int): ByteArray {
    return blow(untouched.toByteArray(Charsets.UTF_8), key, mode)
}

fun blow(untouched: ByteArray, key: String, mode: Int): ByteArray {
    // create a cipher based upon Blowfish
    val cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding")

    // initialise cipher to with secret key
    cipher.init(mode, tetrodotoxin(key))

    // encrypt message
    return cipher.doFinal(untouched)
}

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