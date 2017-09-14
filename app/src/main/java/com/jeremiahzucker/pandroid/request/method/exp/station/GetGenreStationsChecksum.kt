package com.jeremiahzucker.pandroid.request.method.exp.station

import com.jeremiahzucker.pandroid.request.method.Method
import com.jeremiahzucker.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#checksum
 */
object GetGenreStationsChecksum: Method() {

    data class RequestBody(
            val includeGenreCategoryAdUrl: Boolean?
    ) : SyncTokenRequestBody(TokenType.USER)

    data class ResponseBody(
            val checksum: String
    )

}