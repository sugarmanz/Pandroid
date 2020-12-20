package com.jeremiahzucker.pandroid.extensions

import io.ktor.client.features.logging.Logger

// TODO: Add debug check
fun <T> T.log(block: (T) -> String = { it.toString() }) = also {
    it.let(block).let(::println)
}

// fun <T> T.composeLogger(block2: (T) -> String = { it.toString() }) =
//     fun T.(block: (T) -> String) = log(block.let(block2))

// fun <T> Logger.composeLogger(formatter: (String) -> String = { it }): T.((T) -> String) -> T = { transform -> also {
//     this@composeLogger.log(formatter(transform(it)))
// } }
// inline fun <reified T> Logger.composeLogger2(formatter: (String) -> String = { it }) = fun T.(transform: (T) -> String) = also {
//     this@composeLogger2.log(formatter(transform(it)))
// }


