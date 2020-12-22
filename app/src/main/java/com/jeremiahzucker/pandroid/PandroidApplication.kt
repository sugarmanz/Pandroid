package com.jeremiahzucker.pandroid

import android.app.Application
import com.jeremiahzucker.pandroid.cache.DatabaseDriverFactory
import com.jeremiahzucker.pandroid.cache.Preferences
import io.realm.Realm

class PandroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(applicationContext)
//        Preferences.init(applicationContext)
        initPreferences()

        pandoraSdk = PandoraSdk(DatabaseDriverFactory(this))
    }

    companion object {
        fun Application.initPreferences() {
            Preferences.initialize(this)
        }

        lateinit var pandoraSdk: PandoraSdk
    }
}
