package com.jz.pandroid.crypt

import com.jz.pandroid.crypt.BlowFish
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

    @Test
    fun testAlgoChanges() {
        val algorithm = "algorithm"
        val algorithmStrategy = "algorithmStrategy"
        val paddingStrategy = "paddingStrategy"
        val transformation = "$algorithm/$algorithmStrategy/$paddingStrategy"
        val fugu = BlowFish(algorithm = algorithm,
                algorithmStrategy = algorithmStrategy,
                paddingStrategy = paddingStrategy)

        Assert.assertEquals(transformation, fugu.transformation)

        val newAlgorithm = "notTheSameAlgorithm"
        val newAlgorithmStrategy = "notTheSameAlgorithmStrategy"
        val newPaddingStrategy = "notTheSamePaddingStrategy"
        val newTransformation = "$newAlgorithm/$newAlgorithmStrategy/$newPaddingStrategy"
        fugu.algorithm = newAlgorithm
        fugu.algorithmStrategy = newAlgorithmStrategy
        fugu.paddingStrategy = newPaddingStrategy

        Assert.assertEquals(newTransformation, fugu.transformation)
    }

}