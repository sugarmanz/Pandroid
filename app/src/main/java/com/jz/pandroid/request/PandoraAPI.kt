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
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonSerializer
import java.lang.reflect.Type


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
                        @Body requestModel: MyRequestBody): Call<ResponseModel>

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
    get() = GsonBuilder().registerTypeAdapter(EncryptedRequest::class.java, EncryptionSerializer()).create()

// This gson instance will make sure to grab the subclass of the MyRequestBody in order to preserve
// serialization and make the retrofit2 API declaration bearable. This still needs to incorporate
// the encryption serializer to ensure that the bodies get encrypted
val gson2: Gson
    get() {
        return GsonBuilder()
                .registerTypeAdapter(MyRequestBody::class.java, JsonSerializer<MyRequestBody> {
                    src, typeOfSrc, context -> context.serialize(src, src::class.java)
                }).create()
    }

fun buildPandoraAPI() = Retrofit.Builder()
        .baseUrl("https://tuner.pandora.com/services/json/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson2))
        .build()

open class MyRequestBody