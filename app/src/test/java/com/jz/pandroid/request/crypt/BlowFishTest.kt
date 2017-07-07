package com.jz.pandroid.request.crypt

import org.junit.Assert
import org.junit.Test

class BlowFishTest {

    @Test
    @Throws(Exception::class)
    fun blowSoHard() {
        val value = "I am a test value!"

        // Test encryption key
        val encrypted = encrypt(value, EKEY)
        val decrypted = decrypt(encrypted, EKEY)

        Assert.assertEquals(value, String(decrypted, Charsets.UTF_8))

        // Test decryption key
        val encrypted2 = encrypt(value, DKEY)
        val decrypted2 = decrypt(encrypted2, DKEY)

        Assert.assertEquals(value, String(decrypted2, Charsets.UTF_8))
    }

}