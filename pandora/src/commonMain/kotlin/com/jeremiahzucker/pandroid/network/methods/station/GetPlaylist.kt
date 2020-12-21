package com.jeremiahzucker.pandroid.network.methods.station

import com.jeremiahzucker.pandroid.audio.AudioUrlFormat
import com.jeremiahzucker.pandroid.models.TrackModel
import com.jeremiahzucker.pandroid.network.methods.BaseMethod
import com.jeremiahzucker.pandroid.network.methods.UserRequestBody
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/play/#station-getplaylist
 */
object GetPlaylist : BaseMethod() {

    @Serializable
    data class RequestBody(
        val stationToken: String,
        @Transient
        val additionalAudioUrlList: List<AudioUrlFormat> = emptyList(),
        val includeTrackLength: Boolean = true,
        val stationIsStarting: Boolean = false,
        val includeAudioToken: Boolean = false,
        val xplatfromAdCapable: Boolean = false,
        val includeAudioReceiptUrl: Boolean = false,
        val includeBackstageAdUrl: Boolean = false,
        val includeSharingAdUrl: Boolean = false,
        val includeSocialAdUrl: Boolean = false,
        val includeCompetitiveSepIndicator: Boolean = false,
        val includeCompletePlaylist: Boolean = false,
        val includeTrackOptions: Boolean = false,
        val audioAdPodCapable: Boolean = false
    ) : UserRequestBody() {
        val additionalAudioUrl: String = AudioUrlFormat.buildAudioUrlString(additionalAudioUrlList)
    }

    @Serializable
    data class ResponseBody(
        val items: List<TrackModel>
    )
}
