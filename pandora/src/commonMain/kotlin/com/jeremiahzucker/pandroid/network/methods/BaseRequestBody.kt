package com.jeremiahzucker.pandroid.network.methods

import com.jeremiahzucker.pandroid.cache.Preferences
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class BaseRequestBody {
    val syncTime: Long = (Preferences.syncTimeOffset ?: 0L) + (System.currentTimeMillis() / 1000L)
}

@Serializable
abstract class UserRequestBody : BaseRequestBody() {
    val userAuthToken = Preferences.userAuthToken
}

@Serializable
abstract class PartnerRequestBody : BaseRequestBody() {
    val partnerAuthToken = Preferences.partnerAuthToken
}