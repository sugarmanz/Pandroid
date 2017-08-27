package com.jeremiahzucker.pandroid.request.method.exp.user

import com.jeremiahzucker.pandroid.request.method.Method
import com.jeremiahzucker.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/account/#user-emailpassword
 */
object EmailPassword: Method() {
    data class RequestBody(
            val username: String
    ) : SyncTokenRequestBody(TokenType.PARTNER)
}