package com.jeremiahzucker.pandroid.request.json.v5.method.station

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.DateModel
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/play/#station-addfeedback
 */
object AddFeedback: BaseMethod() {
    data class RequestBody(
            val stationToken: String,
            val trackToken: String,
            val isPositive: Boolean
    ) : SyncTokenRequestBody(TokenType.USER)

    data class ResponseBody(
            val totalThumbsDown: Int,
            val stationPersonalizedPercent: Int,
            val dateCreated: DateModel,
            val albumArtUrl: String,
            val musicToken: String,
            val songName: String,
            val artistName: String,
            val totalThumbsUp: Int,
            val feedbackId: String,
            val isPositive: Boolean
    )
}