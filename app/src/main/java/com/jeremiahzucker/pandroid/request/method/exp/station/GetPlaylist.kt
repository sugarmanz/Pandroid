package com.jeremiahzucker.pandroid.request.method.exp.station

import com.google.gson.annotations.Expose
import com.jeremiahzucker.pandroid.request.audio.AudioUrlFormat
import com.jeremiahzucker.pandroid.request.method.Method
import com.jeremiahzucker.pandroid.request.model.SyncTokenRequestBody
import com.jeremiahzucker.pandroid.request.model.TrackModel

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/play/#station-getplaylist
 */
object GetPlaylist: Method() {

    data class RequestBody(
            val stationToken: String,
            @Expose(serialize = false)
            val additionalAudioUrlList: List<AudioUrlFormat>? = null,
            val stationIsStarting: Boolean? = null,
            val includeTrackLength: Boolean? = null,
            val includeAudioToken: Boolean? = null,
            val xplatfromAdCapable: Boolean? = null,
            val includeAudioReceiptUrl: Boolean? = null,
            val includeBackstageAdUrl: Boolean? = null,
            val includeSharingAdUrl: Boolean? = null,
            val includeSocialAdUrl: Boolean? = null,
            val includeCompetitiveSepIndicator: Boolean? = null,
            val includeCompletePlaylist: Boolean? = null,
            val includeTrackOptions: Boolean? = null,
            val audioAdPodCapable: Boolean? = null
    ) : SyncTokenRequestBody(TokenType.USER) {
        val additionalAudioUrl: String? = AudioUrlFormat.buildAudioUrlString(additionalAudioUrlList)
    }

    data class ResponseBody(
            val items: List<TrackModel>
    )


}