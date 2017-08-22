package com.jz.pandroid.request.method.exp.ad

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/ads/#ad-getadmetadata
 */
object GetAdMetadata: Method() {
    data class RequestBody(
            val adToken: String,
            val returnAdTrackingTokens: Boolean?,
            val supportAudioAds: Boolean?,
            val includeBannerAd: Boolean?
    ) : SyncTokenRequestBody(TokenType.USER)
}