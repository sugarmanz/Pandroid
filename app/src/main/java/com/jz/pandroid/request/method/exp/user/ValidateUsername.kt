package com.jz.pandroid.request.method.exp.user

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/account/#user-validateusername
 */
object ValidateUsername: Method() {
    data class RequestBody(
            val username: String
    ) : SyncTokenRequestBody(TokenType.PARTNER)

    data class ResponseBody(
            val isValid: Boolean,
            val isUnique: Boolean
    )
}