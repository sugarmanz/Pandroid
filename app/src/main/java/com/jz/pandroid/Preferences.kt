package com.jz.pandroid

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
}
