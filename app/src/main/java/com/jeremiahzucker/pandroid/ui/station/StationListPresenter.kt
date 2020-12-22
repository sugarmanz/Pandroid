package com.jeremiahzucker.pandroid.ui.station

import android.util.Log
import com.jeremiahzucker.pandroid.PandroidApplication.Companion.pandoraSdk
import com.jeremiahzucker.pandroid.cache.Preferences

/**
 * StationListPresenter
 *
 * Author: Jeremiah Zucker
 * Date:   9/2/2017
 * Desc:   TODO: Complete
 */
class StationListPresenter : StationListContract.Presenter {

    private val TAG: String = StationListPresenter::class.java.simpleName
    private var view: StationListContract.View? = null

    override fun attach(view: StationListContract.View) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    override suspend fun getStations() = try {
        pandoraSdk.getStations()
    } catch (exception: Exception) {
        Log.e(TAG, exception.message, exception)
        view?.showProgress(false)
        emptyList()
    }

    private suspend fun reAuthenticate(success: suspend () -> Unit, failure: suspend () -> Unit) {
        val username = Preferences.username
        val password = Preferences.password

        if (username != null && password != null) {
            try {
                pandoraSdk.authenticate(username, password)
            } catch (exception: Exception) {
                Log.e(TAG, exception.message, exception)
                view?.showAuth()
            }
        } else view?.showAuth()
    }
}
