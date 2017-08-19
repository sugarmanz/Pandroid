package com.jz.pandroid.util

import org.junit.Assert
import org.junit.Test

class HexUtilTest {

    @Test
    fun testByteArrayToHexString() {
        val bytes = byteArrayOf(-1, 0, 1, 2, 3)
        Assert.assertEquals(bytes.bytesToHex(), "ff00010203")
    }

}