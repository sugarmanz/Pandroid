package com.jeremiahzucker.pandroid.network.crypto

import com.jeremiahzucker.pandroid.crypto.Cipher
import com.jeremiahzucker.pandroid.extensions.toHexString
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpClientFeature
import io.ktor.client.features.feature
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.util.AttributeKey

/** Simple client feature to encrypt request bodies with the presence of an [ENC_HEADER_TAG] */
object Encryption : HttpClientFeature<Cipher.Config, Cipher> {

    const val ENC_HEADER_TAG = "ENCRYPT_ME_PLZ"

    override val key: AttributeKey<Cipher> = AttributeKey("ClientEncryption")

    override fun prepare(block: Cipher.Config.() -> Unit) =
        Cipher.Config().apply(block).build()

    override fun install(feature: Cipher, scope: HttpClient) {
        val logger = scope.feature(Logging)?.logger ?: Logger.DEFAULT

        fun <T> T.log(block: (T) -> String = { it.toString() }) = also {
            logger.log("${key.name}: ${block(it)}")
        }

        scope.requestPipeline.intercept(HttpRequestPipeline.Render) {
            log { "render pipeline interceptor" }
            if (context.headers.contains(ENC_HEADER_TAG)) {
                log { "encrypt header found" }
                proceedWith((subject as? TextContent)?.run {
                    context.headers.remove(ENC_HEADER_TAG)
                    TextContent(
                        feature.encrypt(text.log { "raw: $it" }).toHexString()
                            .log { "encrypted: $it" },
                        ContentType.Text.Plain.withCharset(Charsets.UTF_8)
                    )
                } ?: subject)
            } else proceed()
        }
    }

    /** convenience helper to add the encrypt header */
    public fun HttpRequestBuilder.encrypt() {
        header(ENC_HEADER_TAG, "true")
    }
}