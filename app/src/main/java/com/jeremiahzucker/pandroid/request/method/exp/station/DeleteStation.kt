package com.jeremiahzucker.pandroid.request.method.exp.station

import com.jeremiahzucker.pandroid.request.method.Method
import com.jeremiahzucker.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#delete-station
 */
object DeleteStation: Method() {

    data class RequestBody(
            val stationToken: String
    ) : SyncTokenRequestBody(TokenType.USER)

}