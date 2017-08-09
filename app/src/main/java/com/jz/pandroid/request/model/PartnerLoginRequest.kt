package com.jz.pandroid.request.model

/**
 * Created by jzucker on 7/1/17.
 */
data class PartnerLoginRequest(val username: String,
                               val password: String,
                               val deviceModel: String,
                               val version: String)


// TODO: Should probably not be
object PartnerLogin {
    val methodName = "auth.partnerLogin"
    val username = "android"
    val password = "AC7IBG09A3DTSYM4R41UJWL07VLN8JI7"
    val deviceModel = "android-generic"
    val version = "5"
}