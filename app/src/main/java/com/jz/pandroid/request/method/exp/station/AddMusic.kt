package com.jz.pandroid.request.method.exp.station

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#station-addmusic
 */
object AddMusic: Method() {
    data class RequestBody(
            val stationToken: String,
            val trackToken: String
    ) : SyncTokenRequestBody(TokenType.USER)

    data class ResponseBody(
            val seedId: String,
            val artUrl: String,
            val musicToken: String,
            val artistName: String
    )
}