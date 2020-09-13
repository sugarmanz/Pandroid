package com.jeremiahzucker.pandroid.request.json.v5.method.exp.user

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/account/#user-createuser
 */
object CreateUser : BaseMethod() {
    data class RequestBody(
        val username: String,
        val password: String,
        val gender: String,
        val birthYear: Int,
        val zipCode: Int,
        val emailOptInt: Boolean,
        val countryCode: String,
        val accountType: String = "registered",
        val registeredType: String = "user",
        val includePandoraOneInfo: Boolean,
        val includeAccountMessage: Boolean,
        val returnCollectTrackLifetimeStats: Boolean,
        val returnIsSubscriber: Boolean,
        val xplatformAdCapable: Boolean,
        val includeFacebook: Boolean,
        val includeGoogleplay: Boolean,
        val includeShowUserRecommendations: Boolean,
        val includeAdvertiserAttributes: Boolean
    ) : SyncTokenRequestBody(TokenType.PARTNER)
}
