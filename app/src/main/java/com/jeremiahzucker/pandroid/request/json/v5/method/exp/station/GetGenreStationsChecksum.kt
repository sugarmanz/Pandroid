package com.jeremiahzucker.pandroid.request.json.v5.method.exp.station

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#checksum
 */
object GetGenreStationsChecksum: BaseMethod() {

    data class RequestBody(
            val includeGenreCategoryAdUrl: Boolean?
    ) : SyncTokenRequestBody(TokenType.USER)

    data class ResponseBody(
            val checksum: String
    )

}