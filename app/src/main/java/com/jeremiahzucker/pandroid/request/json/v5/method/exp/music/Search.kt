package com.jeremiahzucker.pandroid.request.json.v5.method.exp.music

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#music-search
 */
object Search : BaseMethod() {
    data class RequestBody(
        val searchText: String,
        val includeNearMatches: Boolean? = null,
        val includeGenreStations: Boolean? = null
    ) : SyncTokenRequestBody(TokenType.USER)
}
