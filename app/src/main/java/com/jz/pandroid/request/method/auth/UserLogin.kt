package com.jz.pandroid.request.method.auth

import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by jzucker on 7/2/17.
 */
object UserLogin {
    data class RequestBody(
        val username: String,
        val password: String,
        val loginType: String = "user"
    ) : SyncTokenRequestBody(TokenType.PARTNER)

    val methodName: String = "auth.userLogin"
}