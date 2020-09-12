package com.jeremiahzucker.pandroid

import android.app.Application
import com.jeremiahzucker.pandroid.persist.Preferences
import io.realm.Realm

class PandroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(applicationContext)
//        Preferences.init(applicationContext)
        Preferences = Preferences(this)
    }

    companion object {
        lateinit var Preferences: Preferences private set
    }

}