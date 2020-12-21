package com.jeremiahzucker.pandroid.extensions

fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }
