package com.jeremiahzucker.pandroid.request.json.v5.method.user

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#user-getstationlist
 */
object GetStationList: BaseMethod() {
    data class RequestBody(
            val includeStationArtUrl: Boolean? = true,
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