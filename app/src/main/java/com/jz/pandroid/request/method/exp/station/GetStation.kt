package com.jz.pandroid.request.method.exp.station

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.DateModel
import com.jz.pandroid.request.model.GeneralFeedbackModel
import com.jz.pandroid.request.model.StationSeedsModel
import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#retrieve-extended-station-information
 */
object GetStation: Method() {

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