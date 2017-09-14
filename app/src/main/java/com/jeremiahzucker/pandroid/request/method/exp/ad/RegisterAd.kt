package com.jeremiahzucker.pandroid.request.method.exp.ad

import com.jeremiahzucker.pandroid.request.method.Method
import com.jeremiahzucker.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/ads/#register-advertisement
 */
object RegisterAd: Method() {
    data class RequestBody(
            val stationId: String?,
            val adTrackingTokens: String
    ) : SyncTokenRequestBody(TokenType.USER)
}