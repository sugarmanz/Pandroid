package com.jz.pandroid.request.method.auth

import com.jz.pandroid.request.method.Method

/**
 * Created by jzucker on 7/1/17.
 * https://6xq.net/pandora-apidoc/json/authentication/#partner-login
 */
object PartnerLogin: Method() {
    data class RequestBody(
            val username: String = "android",
            val password: String = "AC7IBG09A3DTSYM4R41UJWL07VLN8JI7",
            val deviceModel: String = "android-generic",
            val version: String = "5",
            val includeUrls: Boolean? = null,
            val returnDeviceType: Boolean? = null,
            val returnUpdatePromptVersions: Boolean? = null
    )
}

// TODO: Coming in newer versions of Kotlin (might be better alternative?)
//class PartnerLogin: Method() {
//    class object {
//        val username = "android"
//        val password = "AC7IBG09A3DTSYM4R41UJWL07VLN8JI7"
//        val deviceModel = "android-generic"
//        val version = "5"
//    }
//}
