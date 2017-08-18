package com.jz.pandroid.request.crypt

import android.util.Log
import okhttp3.*
import okio.Buffer

/**
 * Created by Jeremiah Zucker on 8/18/2017.
 * This interceptor exists to encrypt the body of requests tagged with a specific header
 */
class EncryptionInterceptor: Interceptor {

    private val TAG = EncryptionInterceptor::class.java.simpleName

    companion object {
        const val ENC_HEADER_TAG = "ENCRYPT_ME_PLZ"
    }

    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request()
        if (request != null && request.header(EncryptionInterceptor.ENC_HEADER_TAG) == "true") {
            Log.d(TAG, "Encrypting body!")

            // Get body as string
            val oldBody = request.body()
            val buffer = Buffer()
            oldBody?.writeTo(buffer)
            val oldBodyString = buffer.readUtf8()

            // Perform encryption
            val encryptedByteArray = BlowFish().encrypt(oldBodyString)
            val encryptedString = String(encryptedByteArray, Charsets.UTF_8)

            // Rebuild request TODO: It's possible that the mediatype should be application/json
            val mediaType = MediaType.parse("text/plain; charset=utf-8")
            val newRequestBody = RequestBody.create(mediaType, encryptedString)
            request = request.newBuilder()
                    .removeHeader(EncryptionInterceptor.ENC_HEADER_TAG)
                    .header("Content-Type", newRequestBody.contentType().toString())
                    .header("Content-Length", newRequestBody.contentLength().toString())
                    .method(request.method(), newRequestBody)
                    .build()
        } else {
            Log.d(TAG, "Not encrypting!")
        }
        return chain!!.proceed(request)
    }

}