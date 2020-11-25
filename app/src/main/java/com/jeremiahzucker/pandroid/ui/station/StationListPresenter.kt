package com.jeremiahzucker.pandroid.ui.station

import android.util.Log
import com.jeremiahzucker.pandroid.PandroidApplication.Companion.Preferences
import com.jeremiahzucker.pandroid.request.Pandora
import com.jeremiahzucker.pandroid.request.json.v5.method.auth.UserLogin
import com.jeremiahzucker.pandroid.request.json.v5.method.user.GetStationList
import com.jeremiahzucker.pandroid.request.json.v5.method.user.GetStationListChecksum
import com.jeremiahzucker.pandroid.request.json.v5.model.ExpandedStationModel
import io.realm.Realm

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
    private var realm: Realm = Realm.getDefaultInstance()

    override fun attach(view: StationListContract.View) {
        this.view = view

        // 1. Try to load stations from database
        if (Preferences.stationListChecksum != null) {
            val stations = realm.where(ExpandedStationModel::class.java).findAll()
            Log.i(TAG, stations.toString())
            view.showProgress(false)
            view.updateStationList(stations)

            // Verify checksum
            verifyChecksum()
        } else {
            getStationList()
        }
    }

    override fun detach() {
        this.view = null
    }

    override fun getStationList(body: GetStationList.RequestBody) {
        Pandora.HTTP.RequestBuilder(GetStationList)
            .body(body)
            .build<GetStationList.ResponseBody>()
            .subscribe(this::handleGetStationListSuccess, this::handleGetStationListError)
    }

    private fun handleGetStationListSuccess(responseBody: GetStationList.ResponseBody) {
        Preferences.stationListChecksum = responseBody.checksum
        realm.executeTransaction {
            realm.delete(ExpandedStationModel::class.java)
            realm.insert(responseBody.stations)
        }

        view?.showProgress(false)
        view?.updateStationList(responseBody.stations)
    }

    private fun handleGetStationListError(throwable: Throwable? = null) {
        // Oh no!
        Log.e(TAG, throwable?.message ?: "Error!", throwable)

        view?.showProgress(false)
    }

    private fun verifyChecksum() {
        Log.i(TAG, "VERIFY")
        Pandora.HTTP.RequestBuilder(GetStationListChecksum)
            .body(GetStationListChecksum.RequestBody())
            .build<GetStationListChecksum.ResponseBody>()
            .subscribe(this::handleGetStationListChecksumSuccess, this::handleGetStationListChecksumError)
    }

    private fun handleGetStationListChecksumSuccess(checksum: GetStationListChecksum.ResponseBody) {
        if (checksum.checksum != Preferences.stationListChecksum) getStationList()
    }

    private fun handleGetStationListChecksumError(throwable: Throwable) {
        if ("1001" == throwable.message)
            doUserLogin()
    }

    private fun doUserLogin() {
        Pandora.RequestBuilder(UserLogin)
            .authToken(Preferences.partnerAuthToken)
            .body(UserLogin.RequestBody(Preferences.username ?: "", Preferences.password ?: ""))
            .build<UserLogin.ResponseBody>()
            .subscribe(this::handleUserLoginSuccess, this::handleUserLoginError)
    }

    private fun handleUserLoginSuccess(result: UserLogin.ResponseBody) {
        // TODO: Pass to model? Maybe?
        Preferences.userId = result.userId
        Preferences.userAuthToken = result.userAuthToken

        verifyChecksum()
    }

    private fun handleUserLoginError(throwable: Throwable) {
        view?.showAuth()
    }
}
