package com.jeremiahzucker.pandroid.persist

import com.marcinmoskala.kotlinpreferences.PreferenceHolder

/**
 * Created by jzucker on 7/7/17.
 * SharedPreferences
 */

object Preferences : PreferenceHolder() {
    var syncTimeOffset: Long? by bindToPreferenceFieldNullable()
    var partnerId: String? by bindToPreferenceFieldNullable()
    var partnerAuthToken: String? by bindToPreferenceFieldNullable()
    var userAuthToken: String? by bindToPreferenceFieldNullable()
    var userId: String? by bindToPreferenceFieldNullable()
    var stationListChecksum: String? by bindToPreferenceFieldNullable()
    var username: String? by bindToPreferenceFieldNullable(Keys.USERNAME)
    var password: String? by bindToPreferenceFieldNullable(Keys.PASSWORD)

    fun reset() {
        syncTimeOffset = null
        partnerId = null
        partnerAuthToken = null
        userAuthToken = null
        userId = null
        stationListChecksum = null
    }

    object Keys {
        const val USERNAME = "USERNAME_KEY"
        const val PASSWORD = "PASSWORD_KEY"
    }
}
