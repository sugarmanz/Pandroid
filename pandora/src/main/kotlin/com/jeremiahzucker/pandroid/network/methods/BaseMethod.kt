package com.jeremiahzucker.pandroid.network.methods

import java.util.Locale

abstract class BaseMethod(val shouldEncrypt: Boolean = true) {
    private val subclass get() = this::class.java
    private val regex get() = Regex("package [a-zA-Z0-9.]*")
    private val prefix get() = regex.find(subclass.`package`.toString())?.value?.split(".")?.last()
    private val postfix get() = subclass.simpleName.decapitalize(Locale.ROOT)
    val methodName: String get() = arrayOf(prefix, postfix).joinToString(".")
}