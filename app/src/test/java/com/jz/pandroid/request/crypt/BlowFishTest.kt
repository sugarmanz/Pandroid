package com.jz.pandroid.request.crypt

import org.junit.Assert
import org.junit.Test

class BlowFishTest {

    @Test
    @Throws(Exception::class)
    fun blowSoHard() {
        val value = "I am a test value!"
        val key = "assodijlfk"
        val fugu = BlowFish(key, key)

        val encrypted = fugu.encrypt(value)
        val decrypted = fugu.decrypt(encrypted)

        Assert.assertEquals(value, String(decrypted, Charsets.UTF_8))
    }

}