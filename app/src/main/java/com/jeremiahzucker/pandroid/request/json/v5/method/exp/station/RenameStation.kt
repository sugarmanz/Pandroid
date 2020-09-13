package com.jeremiahzucker.pandroid.request.json.v5.method.exp.station

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#rename-station
 */
object RenameStation : BaseMethod() {

    data class RequestBody(
        val stationToken: String,
        val stationName: String // TODO: Figure out char limit
    ) : SyncTokenRequestBody(TokenType.USER)
}
