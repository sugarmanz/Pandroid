package com.jz.pandroid.request.crypt

import android.util.Log
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

/**
 * Created by jzucker on 6/30/17.
 */

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

//fun blow() {
//    // Key
//    val Key = "Something"
//    val KeyData = Key.toByteArray()
//    val KS = SecretKeySpec(KeyData, "Blowfish")
//
//    // create a key generator based upon the Blowfish cipher
//    val keygenerator = KeyGenerator.getInstance("Blowfish")
//
//    // create a key
//    val secretkey = keygenerator.generateKey()
//
//    // create a cipher based upon Blowfish
//    val cipher = Cipher.getInstance("Blowfish")
//
//    // initialise cipher to with secret key
//    cipher.init(Cipher.ENCRYPT_MODE, KS)
//
//    // get the text to encrypt
//    val inputText = "MyTextToEncrypt"
//
//    // encrypt message
//    val encrypted = cipher.doFinal(inputText.toByteArray())
//}

val DEFAULT_KEY = "AC7IBG09A3DTSYM4R41UJWL07VLN8JI7"
val TAG = "BlowFish"

fun encrypt(decrypted: String, key: String = DEFAULT_KEY): ByteArray {
    return blow(decrypted, key, Cipher.ENCRYPT_MODE)
}

fun decrypt(encrypted: String, key: String = DEFAULT_KEY): ByteArray {
    return blow(encrypted, key, Cipher.DECRYPT_MODE)
}

fun encrypt(decrypted: ByteArray, key: String = DEFAULT_KEY): ByteArray {
    return blow(decrypted, key, Cipher.ENCRYPT_MODE)
}

fun decrypt(encrypted: ByteArray, key: String = DEFAULT_KEY): ByteArray {
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
    val cipher = Cipher.getInstance("Blowfish/ECB/NoPadding")

    // initialise cipher to with secret key
    cipher.init(mode, tetrodotoxin(key))

    // encrypt message
    return cipher.doFinal(untouched)
}

fun bytesToHex(array: ByteArray): String {
    val builder = StringBuilder()
    for (b in array) {
        builder.append(String.format("%02x", b))
    }
    return builder.toString()
}

fun blowSoHard(orig: String = "ddc6bb7663274e6cecb7d519d452219a") {
    Log.i(TAG, "Testing blow!")

    Log.i(TAG, "Orig: " + orig)
    val encrypted = encrypt(orig)
    Log.i(TAG, "Encr: " + encrypted)
    Log.i(TAG, "Encr: " + String(encrypted, Charsets.UTF_8))
    val decrypted = decrypt(encrypted)
    Log.i(TAG, "Decr: " + String(decrypted, Charsets.UTF_8))
}