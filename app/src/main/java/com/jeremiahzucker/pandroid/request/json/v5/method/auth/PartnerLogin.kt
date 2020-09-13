package com.jeremiahzucker.pandroid.request.json.v5.method.auth

import com.jeremiahzucker.pandroid.request.BaseMethod

/**
 * Created by jzucker on 7/1/17.
 * https://6xq.net/pandora-apidoc/json/authentication/#partner-login
 */
object PartnerLogin : BaseMethod() {
    data class RequestBody(
        val username: String = "android",
        val password: String = "AC7IBG09A3DTSYM4R41UJWL07VLN8JI7",
        val deviceModel: String = "android-generic",
        val version: String = "5",
        val includeUrls: Boolean? = null,
        val returnDeviceType: Boolean? = null,
        val returnUpdatePromptVersions: Boolean? = null
    )

    data class ResponseBody(
        val syncTime: String,
        val partnerId: String,
        val partnerAuthToken: String
    )
}
