package com.jeremiahzucker.pandroid.network

import com.jeremiahzucker.pandroid.cache.Preferences
import com.jeremiahzucker.pandroid.models.Response
import com.jeremiahzucker.pandroid.models.Station
import com.jeremiahzucker.pandroid.network.crypt.BlowFish
import com.jeremiahzucker.pandroid.network.methods.BaseMethod
import com.jeremiahzucker.pandroid.network.methods.auth.PartnerLogin
import com.jeremiahzucker.pandroid.network.methods.auth.UserLogin
import io.ktor.client.HttpClient
import io.ktor.client.features.Charsets
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.withCharset
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PandoraApi {

    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        classDiscriminator = "stat"
    }

    private val httpClient = HttpClient {
        Charsets {
            // Allow to use `UTF_8`.
            register(Charsets.UTF_8)
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
            acceptContentTypes = acceptContentTypes + listOf(ContentType.Text.Plain.withCharset(Charsets.UTF_8))
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    suspend fun partnerLogin(): Response<PartnerLogin.ResponseBody> = PartnerLogin.call {
        body = PartnerLogin.RequestBody()
    }

    suspend fun userLogin(username: String, password: String): Response<UserLogin.ResponseBody> = UserLogin.call {
        parameter("partner_id", Preferences.partnerId)
        parameter("auth_token", Preferences.partnerAuthToken)

        // Get body as string
        val bodyStr = json.encodeToString(UserLogin.RequestBody(username, password)).also(::println)

        // Perform encryption
        val blowFish = BlowFish()
        val encryptedByteArray = blowFish.encrypt(bodyStr)
        val encodedString = encryptedByteArray.bytesToHex()

        // println()
        // println(blowFish.decrypt(encryptedByteArray))
        // println(blowFish.decrypt(encodedString))
        // println()

        body = encodedString

        headers {
            contentType(ContentType.Text.Plain.withCharset(Charsets.UTF_8))
        }
        // Rebuild request
        // val mediaType = MediaType.parse("text/plain; charset=utf-8")
        // val newRequestBody = RequestBody.create(mediaType, encodedString)
        // request.newBuilder()
        //     .removeHeader(ENC_HEADER_TAG)
        //     .header("Content-Type", newRequestBody.contentType().toString())
        //     .header("Content-Length", newRequestBody.contentLength().toString())
        //     .method(request.method(), newRequestBody)
        //     .build()
    }

    suspend fun getStations(): List<Station> = httpClient.get(STATIONS_ENDPOINT)

    private suspend inline fun <reified T> BaseMethod.call(block: HttpRequestBuilder.() -> Unit): T = httpClient.post(BASE_ENDPOINT) {
        parameter("method", methodName)
        contentType(ContentType.Application.Json)
        block()
    }

    // Credit where credit is due
    // https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    private val hexArray = "0123456789abcdef".toCharArray()
    private fun ByteArray.bytesToHex(): String {
        val hexChars = CharArray(size * 2)
        for (j in indices) {
            val v = this[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v.ushr(4)]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }

    companion object {
        private const val BASE_ENDPOINT = "https://tuner.pandora.com/services/json/"
        private const val BASE_JSON_ENDPOINT = "https://www.pandora.com/api"
        private const val AUTH = "$BASE_ENDPOINT/v1/auth/login"
        private const val STATIONS_ENDPOINT = "https://"
    }
}