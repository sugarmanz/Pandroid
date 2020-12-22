package com.jeremiahzucker.pandroid.cache

import kotlin.reflect.KProperty

actual object Preferences {

    actual var username: String? by LogDelegate()
    actual var password: String? by LogDelegate()
    actual var stationListChecksum: String? by LogDelegate()

    actual var partnerId: String? by LogDelegate()
    actual var userId: String? by LogDelegate()
    actual var userAuthToken: String? by LogDelegate()
    actual var partnerAuthToken: String? by LogDelegate()
    actual var syncTimeOffset: Long? by LogDelegate()

    private class LogDelegate<T>(private val persistentValue: T? = null) {

        private var backing: T? = null

        operator fun getValue(preferences: Preferences, property: KProperty<*>): T? {
            println("getting ${property.name}: $backing")
            return persistentValue ?: backing
        }

        operator fun setValue(preferences: Preferences, property: KProperty<*>, value: T?) {
            println("setting ${property.name}: $value")
            backing = value
        }

    }
}