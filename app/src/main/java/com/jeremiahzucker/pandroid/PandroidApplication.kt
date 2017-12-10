package com.jeremiahzucker.pandroid

import android.app.Application
import com.marcinmoskala.kotlinpreferences.PreferenceHolder
import io.realm.Realm

/**
 * Created by jzucker on 7/7/17.
 */
class PandroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceHolder.setContext(applicationContext)
        Realm.init(applicationContext)
    }
}