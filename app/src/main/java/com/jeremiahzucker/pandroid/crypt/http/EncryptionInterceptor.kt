package com.jeremiahzucker.pandroid.crypt.http

import com.jeremiahzucker.pandroid.crypt.BlowFish
import com.jeremiahzucker.pandroid.util.bytesToHex
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer

/**
 * Created by Jeremiah Zucker on 8/18/2017.
 * This interceptor exists to encrypt the body of requests tagged with a specific header
 */
class EncryptionInterceptor : Interceptor {

    companion object {
        const val ENC_HEADER_TAG = "ENCRYPT_ME_PLZ"
    }

    private val TAG = EncryptionInterceptor::class.java.simpleName

    override fun intercept(chain: Interceptor.Chain) = chain.request().let { request ->
        if (request.header(ENC_HEADER_TAG) == "true") {
            // Get body as string
            val oldBody = request.body()
            val buffer = Buffer()
            oldBody?.writeTo(buffer)
            val oldBodyString = buffer.readUtf8()

            // Perform encryption
            val blowFish = BlowFish()
            val encryptedByteArray = blowFish.encrypt(oldBodyString)
            val encodedString = encryptedByteArray.bytesToHex()

            // Rebuild request
            val mediaType = MediaType.parse("text/plain; charset=utf-8")
            val newRequestBody = RequestBody.create(mediaType, encodedString)
            request.newBuilder()
                .removeHeader(ENC_HEADER_TAG)
                .header("Content-Type", newRequestBody.contentType().toString())
                .header("Content-Length", newRequestBody.contentLength().toString())
                .method(request.method(), newRequestBody)
                .build().also(::println)
        } else request
    }.let(chain::proceed)
}
