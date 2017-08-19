package com.jz.pandroid.request

import com.jz.pandroid.crypt.http.EncryptionInterceptor
import com.jz.pandroid.request.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.*


/**
 * Created by jzucker on 6/30/17.
 * https://tuner.pandora.com/services/json/
 */
class Pandora {

    // Create companion object to hold constants relative to this domain
    companion object {
        private const val PANDORA_API_BASE_URL = "https://tuner.pandora.com/services/json/"
    }

    // Retrofit2 interface
    interface PandoraAPI {
        @POST("./")
        fun attemptPOST(@Query(value = "method", encoded = true) method: String? = null,
                        @Query(value = "partner_id", encoded = true) partnerId: String? = null,
                        @Query(value = "auth_token", encoded = true) authToken: String? = null,
                        @Query(value = "user_id", encoded = true) userId: String? = null,
                        @Header(value = EncryptionInterceptor.ENC_HEADER_TAG) encrypted: Boolean = true,
                        @Body requestModel: Any?): Call<ResponseModel>
    }

    val API: PandoraAPI by lazy {
        Retrofit.Builder()
                .baseUrl(PANDORA_API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PandoraAPI::class.java)
    }

    private val client: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val encryptionInterceptor = EncryptionInterceptor()

        OkHttpClient.Builder()
            // Ensure the logging interceptor is in the chain before the encryption interceptor
            .addInterceptor(loggingInterceptor)
            .addInterceptor(encryptionInterceptor)
            .build()
    }

}
