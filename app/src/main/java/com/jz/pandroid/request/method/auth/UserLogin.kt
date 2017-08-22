package com.jz.pandroid.request.method.auth

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by jzucker on 7/2/17.
 * https://6xq.net/pandora-apidoc/json/authentication/#user-login
 */
object UserLogin: Method() {
    data class RequestBody(
        val username: String,
        val password: String,
        val loginType: String = "user",
        val returnGenreStations: Boolean? = null,
        val returnCapped: Boolean? = null,
        val includePandoraOneInfo: Boolean? = null,
        val includeDemographics: Boolean? = null,
        val includeAdAttributes: Boolean? = null,
        val returnStationList: Boolean? = null,
        val includeStationArtUrl: Boolean? = null,
        val includeStationSeeds: Boolean? = null,
        val includeShuffleInsteadOfQuickMix: Boolean? = null,
        val stationArtSize: String? = null,
        val returnCollectTrackLifetimeStats: Boolean? = null,
        val returnIsSubscriber: Boolean? = null,
        val xplatformAdCapable: Boolean? = null,
        val complimentarySponsorSupported: Boolean? = null,
        val includeSubscriptionExpiration: Boolean? = null,
        val returnHasUsedTrial: Boolean? = null,
        val returnUserstate: Boolean? = null,
        val includeAccountMessage: Boolean? = null,
        val includeUserWebname: Boolean? = null,
        val includeListeningHours: Boolean? = null,
        val includeFacebook: Boolean? = null,
        val includeTwitter: Boolean? = null,
        val includeDailySkipLimit: Boolean? = null,
        val includeSkipDelay: Boolean? = null,
        val includeGoogleplay: Boolean? = null,
        val includeShowUserRecommendations: Boolean? = null
    ) : SyncTokenRequestBody(TokenType.PARTNER)
}