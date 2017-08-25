package com.jz.pandroid.request.method.exp.user

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/account/#user-cansubscribe
 */
object CanSubscribe: Method() {
    data class RequestBody(
            val iapVendor: String? = null
    ) : SyncTokenRequestBody(TokenType.USER)

    data class ResponseBody(
            val canSubscribe: Boolean,
            val isSubscriber: Boolean
    )
}