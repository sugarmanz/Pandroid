package com.jeremiahzucker.pandroid.ui.station

import android.util.Log
import com.jeremiahzucker.pandroid.request.Pandora
import com.jeremiahzucker.pandroid.request.method.exp.user.GetStationList
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

    override fun attach(view: StationListContract.View) {
        this.view = view

        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.delete(ExpandedStationModel::class.java)
            realm.createObject(ExpandedStationModel::class.java, "SLFDJK").stationName
        }

        Log.i(TAG, realm.where(ExpandedStationModel::class.java).findAll().toString())
        view.updateStationList(realm.where(ExpandedStationModel::class.java).findAll().toList())
        // 1. Try to load stations from database
            // 1. Upon success, check station checksum
                // 1. If no difference, hooray
                // 2. If difference, send network request
            // 2. Upon failure, send network request
        // 2.

        realm.close()
    }

    override fun detach() {
        this.view = null
    }

    override fun getStationList(body: GetStationList.RequestBody) {
        Pandora(Pandora.Protocol.HTTP)
                .RequestBuilder(GetStationList)
                .body(body)
                .build<GetStationList.ResponseBody>()
                .subscribe(this::handleGetStationListSuccess, this::handleGetStationListError)
    }

    private fun handleGetStationListSuccess(responseBody: GetStationList.ResponseBody) {
        if (view == null)
            return

        view?.showProgress(false)
        view?.updateStationList(responseBody.stations)
    }

    private fun handleGetStationListError(throwable: Throwable? = null) {
        // Oh no!
        Log.e(TAG, throwable?.message ?: "Error!", throwable)

        if (view == null)
            return

        view?.showProgress(false)
    }

}