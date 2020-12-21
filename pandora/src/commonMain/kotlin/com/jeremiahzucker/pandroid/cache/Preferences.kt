package com.jeremiahzucker.pandroid.cache

import kotlin.reflect.KProperty

object Preferences {

    var partnerId: String? by LogDelegate()
    var userId: String? by LogDelegate()
    var userAuthToken: String? by LogDelegate()
    var partnerAuthToken: String? by LogDelegate()
    var syncTimeOffset: Long? by LogDelegate()

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