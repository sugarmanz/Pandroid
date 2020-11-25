package com.jeremiahzucker.pandroid.request.json.v5.method.station

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#delete-station
 */
object DeleteFeedback : BaseMethod() {

    data class RequestBody(
        val feedbackId: String
    ) : SyncTokenRequestBody(TokenType.USER)
}
