package com.jz.pandroid.request.method.exp.station

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.*

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#predefined-stations
 */
object GetGenreStations: Method() {

    // TODO: Compare implementations
    class RequestBodyClass : SyncTokenRequestBody(TokenType.USER)
    // vs.
    fun RequestBodyFunction() = SyncTokenRequestBody(SyncTokenRequestBody.TokenType.USER)
    // Function vs. Class

    data class ResponseBody(
            val categories: List<CategoryModel>
    )

}