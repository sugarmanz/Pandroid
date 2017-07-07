package com.jz.pandroid

import com.marcinmoskala.kotlinpreferences.PreferenceHolder

/**
 * Created by jzucker on 7/7/17.
 * SharedPreferences
 */

object Preferences : PreferenceHolder() {
    var syncTime: String? by bindToPreferenceFieldNullable()
}

// TODO: Should probably not be
object PartnerLogin : PreferenceHolder() {
    var methodName = "auth.partnerLogin"
    var partnerUsername = "android"
    var partnerPassword = "AC7IBG09A3DTSYM4R41UJWL07VLN8JI7"
    var deviceType = "android-generic"
    var version = "5"
}