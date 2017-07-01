package com.jz.pandroid.request.crypt

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

/**
 * Created by jzucker on 6/30/17.
 */

fun blow() {
    // Key
    val Key = "Something"
    val KeyData = Key.toByteArray()
    val KS = SecretKeySpec(KeyData, "Blowfish")

    // create a key generator based upon the Blowfish cipher
    val keygenerator = KeyGenerator.getInstance("Blowfish")

    // create a key
    val secretkey = keygenerator.generateKey()

    // create a cipher based upon Blowfish
    val cipher = Cipher.getInstance("Blowfish")

    // initialise cipher to with secret key
    cipher.init(Cipher.ENCRYPT_MODE, KS)

    // get the text to encrypt
    val inputText = "MyTextToEncrypt"

    // encrypt message
    val encrypted = cipher.doFinal(inputText.toByteArray())
}

fun convertToLowerHex() {

}