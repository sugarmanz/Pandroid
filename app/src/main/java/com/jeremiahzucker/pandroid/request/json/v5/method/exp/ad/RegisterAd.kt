package com.jeremiahzucker.pandroid.request.json.v5.method.exp.ad

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/ads/#register-advertisement
 */
object RegisterAd: BaseMethod() {
    data class RequestBody(
            val stationId: String?,
            val adTrackingTokens: String
    ) : SyncTokenRequestBody(TokenType.USER)
}