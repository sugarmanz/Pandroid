package com.jz.pandroid.request.method

/**
 * Created by jzucker on 7/2/17.
 */
object UserLogin {
    data class RequestBody(
        val username: String,
        val password: String,
        val partnerAuthToken: String,
        val syncTime: Long,
        val loginType: String = "user"
    )

    val methodName: String = "auth.userLogin"
}