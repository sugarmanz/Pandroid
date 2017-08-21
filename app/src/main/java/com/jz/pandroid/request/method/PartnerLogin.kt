package com.jz.pandroid.request.method

/**
 * Created by jzucker on 7/1/17.
 */
object PartnerLogin {
    data class RequestBody(
            val username: String = "android",
            val password: String = "AC7IBG09A3DTSYM4R41UJWL07VLN8JI7",
            val deviceModel: String = "android-generic",
            val version: String = "5"
    )

    val methodName: String = "auth.partnerLogin"
}

// TODO: Coming in newer versions of Kotlin (might be better alternative?)
//class PartnerLogin {
//    class object {
//        val username = "android"
//        val password = "AC7IBG09A3DTSYM4R41UJWL07VLN8JI7"
//        val deviceModel = "android-generic"
//        val version = "5"
//    }
//    val methodName = "auth.partnerLogin"
//}
