package com.jeremiahzucker.pandroid.network

import arrow.syntax.function.partially1
import com.jeremiahzucker.pandroid.cache.Preferences
import com.jeremiahzucker.pandroid.crypto.Cipher
import com.jeremiahzucker.pandroid.models.Response
import com.jeremiahzucker.pandroid.models.Station
import com.jeremiahzucker.pandroid.network.crypto.Encryption
import com.jeremiahzucker.pandroid.network.crypto.Encryption.encrypt
import com.jeremiahzucker.pandroid.network.methods.BaseMethod
import com.jeremiahzucker.pandroid.network.methods.auth.PartnerLogin
import com.jeremiahzucker.pandroid.network.methods.auth.UserLogin
import com.jeremiahzucker.pandroid.network.methods.user.GetStationList
import io.ktor.client.HttpClient
import io.ktor.client.features.feature
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.withCharset
import kotlinx.serialization.json.Json

class PandoraApi {

    private val logger: Logger get() = httpClient.feature(Logging)!!.logger

    val cipher: Cipher get() = httpClient.feature(Encryption)!!

    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        classDiscriminator = "stat"
    }

    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
            acceptContentTypes += listOf(ContentType.Text.Plain.withCharset(Charsets.UTF_8))
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        install(Encryption)
    }

    suspend fun partnerLogin(body: PartnerLogin.RequestBody = PartnerLogin.RequestBody()): Response<PartnerLogin.ResponseBody> = PartnerLogin.call<PartnerLogin.ResponseBody> {
        this.body = body
    }

    suspend fun userLogin(body: UserLogin.RequestBody): Response<UserLogin.ResponseBody> = UserLogin.call {
        parameter("partner_id", Preferences.partnerId)
        parameter("auth_token", Preferences.partnerAuthToken)

        this.body = body
    }

    suspend fun getStations(): Response<GetStationList.ResponseBody> = GetStationList.call {
        body = GetStationList.RequestBody()
    }

    private suspend inline fun <reified T> BaseMethod.call(block: HttpRequestBuilder.() -> Unit = {}): Response<T> = httpClient.post(BASE_ENDPOINT) {
        methodParam(methodName)
        Preferences.partnerId?.let(partnerParam)
        Preferences.userId?.let(userParam)

        Preferences.userAuthToken?.let(authParam)
            ?: Preferences.partnerAuthToken?.let(authParam)

        contentType(ContentType.Application.Json)
        block()
        if (shouldEncrypt) encrypt()
    }

    private val HttpRequestBuilder.methodParam get() = partialParameter("method")
    private val HttpRequestBuilder.partnerParam get() = partialParameter("partner_id")
    private val HttpRequestBuilder.userParam get() = partialParameter("user_id")
    private val HttpRequestBuilder.authParam get() = partialParameter("auth_token")

    private fun HttpRequestBuilder.partialParameter(key: String) = ::parameter.partially1(key)

    companion object {
        private const val BASE_ENDPOINT = "https://tuner.pandora.com/services/json/"
        private const val BASE_JSON_ENDPOINT = "https://www.pandora.com/api"
        private const val AUTH = "$BASE_ENDPOINT/v1/auth/login"
        private const val STATIONS_ENDPOINT = "https://"
    }
}