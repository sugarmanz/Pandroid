package com.jeremiahzucker.pandroid.models

import com.jeremiahzucker.pandroid.cache.Preferences
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Created by Jeremiah Zucker on 8/20/2017.
 */
@Serializable
open class SyncTokenRequestBody(
    @Transient
    val tokenType: TokenType = TokenType.USER
) {

    enum class TokenType(val getToken: () -> String?) {
        USER({ Preferences.userAuthToken }),
        PARTNER({ Preferences.partnerAuthToken }),
        NONE({ null })
    }

    val syncTime: Long = 1607073231

    // Use nullable types to allow GSON to only serialize populated members
    @Transient
    var userAuthToken: String? = null
    var partnerAuthToken: String? = null

    init {
        when (tokenType) {
            TokenType.USER -> {
                userAuthToken = tokenType.getToken()
            }
            TokenType.PARTNER -> {
                partnerAuthToken = tokenType.getToken()
            }
            TokenType.NONE -> {}
        }
    }
}