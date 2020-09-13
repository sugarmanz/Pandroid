package com.jeremiahzucker.pandroid.request.json.v5.method.exp.ad

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/ads/#ad-getadmetadata
 */
object GetAdMetadata : BaseMethod() {
    data class RequestBody(
        val adToken: String,
        val returnAdTrackingTokens: Boolean?,
        val supportAudioAds: Boolean?,
        val includeBannerAd: Boolean?
    ) : SyncTokenRequestBody(TokenType.USER)
}
