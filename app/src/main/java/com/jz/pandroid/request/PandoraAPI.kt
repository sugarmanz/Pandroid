package com.jz.pandroid.request

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jz.pandroid.request.crypt.BlowFish
import com.jz.pandroid.request.crypt.EncryptionSerializer
import com.jz.pandroid.request.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by jzucker on 6/30/17.
 * https://tuner.pandora.com/services/json/
 */
interface PandoraAPI {

    @GET("")
    fun attemptConnection(): Call<ResponseModel>

    @POST("./")
    fun attemptPOST(@Query(value="method", encoded=true) method: String = "",
                        @Query(value="partner_id", encoded=true) partnerId: String = "",
                        @Query(value="auth_token", encoded=true) authToken: String = "",
                        @Query(value="user_id", encoded=true) userId: String = ""): Call<ResponseModel>

    @POST("./")
    fun attemptPOST(@Query(value="method", encoded=true) method: String = "",
                        @Query(value="partner_id", encoded=true) partnerId: String = "",
                        @Query(value="auth_token", encoded=true) authToken: String = "",
                        @Query(value="user_id", encoded=true) userId: String = "",
                        @Body requestModel: PartnerLogin): Call<ResponseModel>
    @POST("./")
    fun attemptPOST(@Query(value="method", encoded=true) method: String = "",
                        @Query(value="partner_id", encoded=true) partnerId: String = "",
                        @Query(value="auth_token", encoded=true) authToken: String = "",
                        @Query(value="user_id", encoded=true) userId: String = "",
                        @Body requestModel: UserLoginRequest): Call<ResponseModel>

    @POST("./")
    fun attemptAuth(): Call<ResponseModel>
}

val client: OkHttpClient
    get() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

val gson: Gson
    get() {
        return GsonBuilder().registerTypeAdapter(EncryptedRequest::class.java, EncryptionSerializer()).create()
    }

fun buildPandoraAPI() = Retrofit.Builder()
        .baseUrl("https://tuner.pandora.com/services/json/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()