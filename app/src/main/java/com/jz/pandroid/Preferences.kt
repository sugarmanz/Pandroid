package com.jz.pandroid

import com.marcinmoskala.kotlinpreferences.PreferenceHolder

/**
 * Created by jzucker on 7/7/17.
 * SharedPreferences
 */

object Preferences : PreferenceHolder() {
    var syncTime: String? by bindToPreferenceFieldNullable()
}
