package com.jz.pandroid.request.model

/**
 * Created by jzucker on 7/1/17.
 */
data class PartnerLoginRequest(val username: String,
                               val password: String,
                               val deviceModel: String,
                               val version: String)
