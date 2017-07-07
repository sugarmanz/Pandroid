package com.jz.pandroid.request.model

/**
 * Created by jzucker on 7/2/17.
 */
data class UserLoginRequest(val username: String,
                            val password: String,
                            val partnerAuthToken: String,
                            val syncTime: String,
                            val loginType: String = "user"): EncryptedRequest()
