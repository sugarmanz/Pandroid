package com.jeremiahzucker.pandroid.cache

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty

// class NullableDatastoreDelegate<V>(private val dataStore: DataStore<Preferences>, private val key: Preferences.Key<V>) {
//
//    companion object {
//        private const val LONG_NULL = Long.MAX_VALUE
//    }
//
//    operator fun getValue(thisRef: Any?, prop: KProperty<*>): V? = runBlocking {
//        dataStore.data.map { it[key] }.map { if (it == LONG_NULL) null else it }.single()
//    }
//
//    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: V?) = dataStore.editBlocking {
//        it[key] = when (value) {
//            is Long -> value
//            else -> value
//        } ?: LONG_NULL as V
//    }
//
//    private fun DataStore<Preferences>.editBlocking(transform: suspend (MutablePreferences) -> Unit): Preferences = runBlocking {
//        edit(transform)
//    }
// }
//
// inline fun <reified V : Any> DataStore<Preferences>.bindToPreferenceFieldNullable(prop: KProperty<*>) = NullableDatastoreDelegate<V>(this, preferencesKey(prop.name))

class NullableSharedPreferencesDelegate<V : Any>(private val sharedPreferences: SharedPreferences, private val dataClass: KClass<V>) {

    companion object {
        private const val LONG_NULL = Long.MAX_VALUE
        private const val STRING_NULL = "I'm a null string baby"
    }

    operator fun getValue(thisRef: Any?, prop: KProperty<*>): V? = when (dataClass) {
        Long::class -> when (val value = sharedPreferences.getLong(prop.name, LONG_NULL)) {
            LONG_NULL -> null
            else -> value
        }
        String::class -> when (val value = sharedPreferences.getString(prop.name, STRING_NULL)) {
            STRING_NULL -> null
            else -> value
        }
        else -> throw IllegalArgumentException("unsupported type $dataClass")
    } as? V

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: V?) = sharedPreferences.edit {
        when (dataClass) {
            Long::class -> putLong(prop.name, value as? Long ?: LONG_NULL)
            String::class -> putString(prop.name, value as? String ?: STRING_NULL)
            else -> throw IllegalArgumentException("unsupported type $dataClass")
        } as? V
    }
}

inline fun <reified V : Any> SharedPreferences.bindToPreferenceFieldNullable() = NullableSharedPreferencesDelegate(this, V::class)

/**
 * Created by jzucker on 7/7/17.
 * SharedPreferences
 */
actual object Preferences {

    private lateinit var backing: Backing

    private class Backing(val context: Context) {
        private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        var syncTimeOffset: Long? by sharedPreferences.bindToPreferenceFieldNullable()
        var partnerId: String? by sharedPreferences.bindToPreferenceFieldNullable()
        var partnerAuthToken: String? by sharedPreferences.bindToPreferenceFieldNullable()
        var userAuthToken: String? by sharedPreferences.bindToPreferenceFieldNullable()
        var userId: String? by sharedPreferences.bindToPreferenceFieldNullable()
        var stationListChecksum: String? by sharedPreferences.bindToPreferenceFieldNullable()
        var username: String? by sharedPreferences.bindToPreferenceFieldNullable()
        var password: String? by sharedPreferences.bindToPreferenceFieldNullable()
    }

    fun initialize(context: Context) {
       backing = Backing(context)
    }

    actual var syncTimeOffset: Long? by BackingDelegate()
    actual var partnerId: String? by BackingDelegate()
    actual var partnerAuthToken: String? by BackingDelegate()
    actual var userAuthToken: String? by BackingDelegate()
    actual var userId: String? by BackingDelegate()
    actual var stationListChecksum: String? by BackingDelegate()
    actual var username: String? by BackingDelegate()
    actual var password: String? by BackingDelegate()

    private class BackingDelegate<T> {

        operator fun getValue(preferences: Preferences, property: KProperty<*>): T? {
            return backing::class.members
                .filterIsInstance<KProperty<T>>()
                .first { it.name == property.name }
                .getter
                .call(backing)
        }

        operator fun setValue(preferences: Preferences, property: KProperty<*>, value: T?) {
            backing::class.members
                .filterIsInstance<KMutableProperty<T>>()
                .first { it.name == property.name }
                .setter
                .call(backing, value)
        }

    }

    fun reset() {
        syncTimeOffset = null
        partnerId = null
        partnerAuthToken = null
        userAuthToken = null
        userId = null
        stationListChecksum = null
    }

}