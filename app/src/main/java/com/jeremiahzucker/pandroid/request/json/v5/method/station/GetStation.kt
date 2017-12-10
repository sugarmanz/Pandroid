package com.jeremiahzucker.pandroid.request.json.v5.method.station

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.DateModel
import com.jeremiahzucker.pandroid.request.json.v5.model.GeneralFeedbackModel
import com.jeremiahzucker.pandroid.request.json.v5.model.StationSeedsModel
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#retrieve-extended-station-information
 * This is currently only used to get the feedback IDs
 */
object GetStation: BaseMethod() {

    data class RequestBody(
            val stationToken: String,
            val includeExtendedAttributes: Boolean
    ) : SyncTokenRequestBody(TokenType.USER)

    data class ResponseBody(
            val music: StationSeedsModel,
            val feedback: GeneralFeedbackModel,

            val suppressVideoAds: Boolean,
            val stationId: String,
            val allowAddMusic: Boolean,
            val dateCreated: DateModel,
            val stationDetailUrl: String,
            val artUrl: String,
            val requiresCleanAds: Boolean,
            val stationToken: String,
            val stationName: String,
            val isShared: Boolean,
            val allowDelete: Boolean,
            val genre: List<String>,
            val isQuickMix: Boolean,
            val allowRename: Boolean,
            val stationSharingUrl: String,
            val allowEditDescription: Boolean
    )

}