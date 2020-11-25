package com.jeremiahzucker.pandroid.request.json.v5.method.exp.station

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.CategoryModel
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#predefined-stations
 */
object GetGenreStations : BaseMethod() {

    // TODO: Compare implementations
    class RequestBodyClass : SyncTokenRequestBody(TokenType.USER)
    // vs.
    fun RequestBodyFunction() = SyncTokenRequestBody(SyncTokenRequestBody.TokenType.USER)
    // Function vs. Class

    data class ResponseBody(
        val categories: List<CategoryModel>
    )
}
