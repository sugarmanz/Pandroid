package com.jeremiahzucker.pandroid.request.json.v5.method.exp.user

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/account/#user-validateusername
 */
object ValidateUsername: BaseMethod() {
    data class RequestBody(
            val username: String
    ) : SyncTokenRequestBody(TokenType.PARTNER)

    data class ResponseBody(
            val isValid: Boolean,
            val isUnique: Boolean
    )
}