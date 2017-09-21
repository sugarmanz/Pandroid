package com.jeremiahzucker.pandroid.ui.station

import android.util.Log
import com.jeremiahzucker.pandroid.Preferences
import com.jeremiahzucker.pandroid.request.Pandora
import com.jeremiahzucker.pandroid.request.method.exp.user.GetStationList
import com.jeremiahzucker.pandroid.request.method.exp.user.GetStationListChecksum
import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel
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
        Pandora.RequestBuilder(GetStationList)
                .protocol(Pandora.Protocol.HTTP)
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
        Pandora.RequestBuilder(GetStationListChecksum)
                .protocol(Pandora.Protocol.HTTP)
                .body(GetStationListChecksum.RequestBody())
                .build<GetStationListChecksum.ResponseBody>()
                .subscribe(this::handleGetStationListChecksumSuccess, this::handleGetStationListChecksumError)
    }

    private fun handleGetStationListChecksumSuccess(checksum: GetStationListChecksum.ResponseBody) {
        if (checksum.checksum != Preferences.stationListChecksum) getStationList()
    }

    private fun handleGetStationListChecksumError(throwable: Throwable) {

    }

}