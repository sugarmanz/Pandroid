package com.jeremiahzucker.pandroid.request

import com.jeremiahzucker.pandroid.BuildConfig
import com.jeremiahzucker.pandroid.Preferences
import com.jeremiahzucker.pandroid.crypt.http.EncryptionInterceptor
import com.jeremiahzucker.pandroid.request.method.Method
import com.jeremiahzucker.pandroid.request.model.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.*


/**
 * Created by jzucker on 6/30/17.
 * https://tuner.pandora.com/services/json/
 */
class Pandora(protocol: Protocol = Protocol.HTTPS) {

    enum class Protocol {
        HTTP,
        HTTPS;

        fun getProtocolString(): String {
            return name.toLowerCase() + "://"
        }
    }

    // Create companion object to hold constants relative to this domain
    companion object {
        private const val PANDORA_API_BASE_URI = "tuner.pandora.com/services/json/"
    }

    private val PANDORA_API_BASE_URL = protocol.getProtocolString() + PANDORA_API_BASE_URI

    // Retrofit2 interface
    private interface PandoraAPI {
        @POST("./")
        fun attemptPOST(
                @Query(value = "method") method: String,
                @Query(value = "partner_id") partnerId: String?,
                @Query(value = "auth_token") authToken: String?,
                @Query(value = "user_id") userId: String?,
                @Header(value = EncryptionInterceptor.ENC_HEADER_TAG) encrypted: Boolean,
                @Body body: Any?): Observable<ResponseModel>
    }

    private val API: PandoraAPI by lazy {
        Retrofit.Builder()
                .baseUrl(PANDORA_API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(PandoraAPI::class.java)
    }

    private val client: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()

        // Ensure the logging interceptor is in the chain before the encryption interceptor
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        val encryptionInterceptor = EncryptionInterceptor()
        builder.addInterceptor(encryptionInterceptor)
        builder.build()
    }

    inner class RequestBuilder(private var method: String) {
        private var partnerId: String? = Preferences.partnerId
        private var authToken: String? = Preferences.userAuthToken
        private var userId: String? = Preferences.userId
        private var encrypted: Boolean = true
        private var body: Any? = null

        constructor(method: Method) : this(method.methodName)

        fun method(method: Method): RequestBuilder {
            this.method = method.methodName
            return this
        }

        fun method(method: String): RequestBuilder {
            this.method = method
            return this
        }

        fun partnerId(partnerId: String?): RequestBuilder {
            this.partnerId = partnerId
            return this
        }

        fun authToken(authToken: String?): RequestBuilder {
            this.authToken = authToken
            return this
        }

        fun userId(userId: String?): RequestBuilder {
            this.userId = userId
            return this
        }

        fun encrypted(encrypted: Boolean): RequestBuilder {
            this.encrypted = encrypted
            return this
        }

        fun body(body: Any?): RequestBuilder {
            this.body = body
            return this
        }

        fun buildResponseModel(): Observable<ResponseModel> = API.attemptPOST(
                method = method,
                partnerId = partnerId,
                authToken = authToken,
                userId = userId,
                encrypted = encrypted,
                body = body
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        inline fun <reified T> build(): Observable<T> = buildResponseModel()
                .filter { it.isOk }
                .map { it.getResult<T>() }
    }

}
