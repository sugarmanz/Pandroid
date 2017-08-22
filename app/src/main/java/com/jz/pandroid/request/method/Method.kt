package com.jz.pandroid.request.method

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * This abstract class exists to limit code duplication and hardcoding for the request methodName
 */
open class Method {

    private val subclass get() = this::class.java
    private val prefix get() = subclass.`package`.toString().split(".").last()
    private val postfix get() = subclass.simpleName.decapitalize()
    val methodName: String get() = arrayOf(prefix, postfix).joinToString(".")

}