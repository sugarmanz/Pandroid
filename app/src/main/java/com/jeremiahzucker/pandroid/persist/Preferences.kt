package com.jeremiahzucker.pandroid.persist

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import kotlin.reflect.KClass
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
class Preferences(application: Application) {

//    fun init(context: Context) {
//        this.context = context
//    }
//
//    private lateinit var context: Context
    private val context: Context = application

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//    private val dataStore: DataStore<Preferences> = context.createDataStore("settings")

//    init {
//        GlobalScope.launch {
//            dataStore.data.first()
//        }
//    }

    var syncTimeOffset: Long? by sharedPreferences.bindToPreferenceFieldNullable()
    var partnerId: String? by sharedPreferences.bindToPreferenceFieldNullable()
    var partnerAuthToken: String? by sharedPreferences.bindToPreferenceFieldNullable()
    var userAuthToken: String? by sharedPreferences.bindToPreferenceFieldNullable()
    var userId: String? by sharedPreferences.bindToPreferenceFieldNullable()
    var stationListChecksum: String? by sharedPreferences.bindToPreferenceFieldNullable()
    var username: String? by sharedPreferences.bindToPreferenceFieldNullable()
    var password: String? by sharedPreferences.bindToPreferenceFieldNullable()

    fun reset() {
        syncTimeOffset = null
        partnerId = null
        partnerAuthToken = null
        userAuthToken = null
        userId = null
        stationListChecksum = null
    }
}
