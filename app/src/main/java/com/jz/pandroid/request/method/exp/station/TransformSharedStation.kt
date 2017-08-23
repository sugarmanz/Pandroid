package com.jz.pandroid.request.method.exp.station

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#transform-shared-station
 */
object TransformSharedStation: Method() {

    data class RequestBody(
            val stationToken: String
    ) : SyncTokenRequestBody(TokenType.USER)

}