package com.jz.pandroid.request.method.exp.music

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#music-search
 */
object Search: Method() {
    data class RequestBody(
            val searchText: String,
            val includeNearMatches: Boolean? = null,
            val includeGenreStations: Boolean? = null
    ) : SyncTokenRequestBody(TokenType.USER)
}