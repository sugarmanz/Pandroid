package com.jeremiahzucker.pandroid.network.methods.user

import com.jeremiahzucker.pandroid.models.ExpandedStationModel
import com.jeremiahzucker.pandroid.network.methods.BaseMethod
import com.jeremiahzucker.pandroid.network.methods.UserRequestBody
import kotlinx.serialization.Serializable

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#user-getstationlist
 */
object GetStationList : BaseMethod() {

    @Serializable
    data class RequestBody(
        val includeStationArtUrl: Boolean = true,
        val stationArtSize: String = "W130H130",
        val includeAdAttributes: Boolean = false,
        val includeStationSeeds: Boolean = false,
        val includeShuffleInsteadOfQuickMix: Boolean = false,
        val includeRecommendations: Boolean = false,
        val includeExplanations: Boolean = false
    ) : UserRequestBody()

    @Serializable
    data class ResponseBody(
        val stations: List<ExpandedStationModel>,
        val checksum: String
    )
}
