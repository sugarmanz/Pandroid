package com.jeremiahzucker.pandroid.request

import com.jeremiahzucker.pandroid.BuildConfig
//import com.jeremiahzucker.pandroid.Preferences
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
object Pandora {

    enum class Protocol {
        HTTP,
        HTTPS;

        fun getProtocolString() = name.toLowerCase() + "://"
        fun buildURL(uri: String = PANDORA_API_BASE_URI) = getProtocolString() + uri
    }

    // Create companion object to hold constants relative to this domain
    private const val PANDORA_API_BASE_URI = "tuner.pandora.com/services/json/"
    val inflightRequests = hashMapOf<RequestBuilder, Observable<ResponseModel>>()

    // Retrofit2 interface
    private interface PandoraAPI {
        @POST
        fun attemptPOST(
                @Url url: String,
                @Query(value = "method") method: String,
                @Query(value = "partner_id") partnerId: String?,
                @Query(value = "auth_token") authToken: String?,
                @Query(value = "user_id") userId: String?,
                @Header(value = EncryptionInterceptor.ENC_HEADER_TAG) encrypted: Boolean,
                @Body body: Any?): Observable<ResponseModel>
    }

    private val API: PandoraAPI by lazy {
        Retrofit.Builder()
                .baseUrl(Protocol.HTTPS.buildURL())
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

    data class RequestBuilder(private var method: String) {
        private var protocol: Protocol = Protocol.HTTPS
        private var partnerId: String? = null//Preferences.partnerId
        private var authToken: String? = null//Preferences.userAuthToken
        private var userId: String? = null//Preferences.userId
        private var encrypted: Boolean = true
        private var body: Any? = null

        constructor(method: Method) : this(method.methodName)

        fun protocol(protocol: Protocol): RequestBuilder {
            this.protocol = protocol
            return this
        }

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

        fun buildResponseModel() = inflightRequests.getOrPut(this) {
            API.attemptPOST(
                    url = protocol.buildURL(),
                    method = method,
                    partnerId = partnerId,
                    authToken = authToken,
                    userId = userId,
                    encrypted = encrypted,
                    body = body
            ).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).share().replay()
        }


        inline fun <reified T> build(): Observable<T> = buildResponseModel()
                .filter { it.isOk }
                .map { it.getResult<T>() }
    }

}
