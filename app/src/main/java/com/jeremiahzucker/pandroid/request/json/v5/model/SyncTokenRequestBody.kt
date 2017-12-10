package com.jeremiahzucker.pandroid.request.json.v5.model

import com.jeremiahzucker.pandroid.persist.Preferences

/**
 * Created by Jeremiah Zucker on 8/20/2017.
 */
open class SyncTokenRequestBody(tokenType: TokenType = TokenType.USER) {

    enum class TokenType(val getToken: () -> String?) {
        USER({ Preferences.userAuthToken }),
        PARTNER({ Preferences.partnerAuthToken }),
        NONE({ null })
    }

    val syncTime: Long = (Preferences.syncTimeOffset ?: 0L) + (System.currentTimeMillis() / 1000L)

    // Use nullable types to allow GSON to only serialize populated members
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
        }
    }

}