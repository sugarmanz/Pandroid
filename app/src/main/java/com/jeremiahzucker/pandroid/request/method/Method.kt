package com.jeremiahzucker.pandroid.request.method

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * This abstract class exists to limit code duplication and hardcoding for the request methodName
 */
abstract class Method {

    private val subclass get() = this::class.java
    private val regex get() = Regex("package [a-zA-Z.]*")
    private val prefix get() = regex.find(subclass.`package`.toString())?.value?.split(".")?.last()
    private val postfix get() = subclass.simpleName.decapitalize()
    val methodName: String get() = arrayOf(prefix, postfix).joinToString(".")

}