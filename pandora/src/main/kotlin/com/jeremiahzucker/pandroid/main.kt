package com.jeremiahzucker.pandroid

import com.jeremiahzucker.pandroid.cache.Preferences
import com.jeremiahzucker.pandroid.models.Response
import com.jeremiahzucker.pandroid.network.PandoraApi

inline fun <reified T> Response<T>.unwrap(): T = success.result

suspend fun main() {
    val api = PandoraApi()
    api.partnerLogin()
        .also(::println)
        .unwrap()
        .also { result ->
            Preferences.partnerId = result.partnerId
            Preferences.partnerAuthToken = result.partnerAuthToken
            Preferences.syncTimeOffset = result.processedSyncTimeOffset
        }
        .let {
            api.userLogin(
                "zucker.jeremiah+pandroid2@gmail.com",
                "pencil"
            )
        }
        .also(::println)
        .unwrap()
}