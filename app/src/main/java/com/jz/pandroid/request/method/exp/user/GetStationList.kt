package com.jz.pandroid.request.method.exp.user

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.ExpandedStationModel
import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#user-getstationlist
 */
object GetStationList: Method() {
    data class RequestBody(
            val includeStationArtUrl: Boolean? = null,
            val stationArtSize: String? = null,
            val includeAdAttributes: Boolean? = null,
            val includeStationSeeds: Boolean? = null,
            val includeShuffleInsteadOfQuickMix: Boolean? = null,
            val includeRecommendations: Boolean? = null,
            val includeExplanations: Boolean? = null
    ) : SyncTokenRequestBody(TokenType.USER)

    data class ResponseBody(
            val stations: List<ExpandedStationModel>,
            val checksum: String
    )
}