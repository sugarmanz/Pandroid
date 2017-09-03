package com.jeremiahzucker.pandroid.station

import android.util.Log
import com.jeremiahzucker.pandroid.auth.AuthContract
import com.jeremiahzucker.pandroid.request.BasicCallback
import com.jeremiahzucker.pandroid.request.Pandora
import com.jeremiahzucker.pandroid.request.method.auth.UserLogin
import com.jeremiahzucker.pandroid.request.method.exp.user.GetStationList
import com.jeremiahzucker.pandroid.request.model.ResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * StationListPresenter
 *
 * Author: Jeremiah Zucker
 * Date:   9/2/2017
 * Desc:   TODO: Complete
 */
class StationListPresenter : StationListContract.Presenter {

    private val TAG: String = StationListPresenter::class.java.simpleName
    var view: StationListContract.View? = null

    override fun attach(view: StationListContract.View) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    override fun getStationList(body: GetStationList.RequestBody) {
        Pandora(Pandora.Protocol.HTTP)
                .RequestBuilder(GetStationList)
                .body(body)
                .buildObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { it.isOk }
                .map { it.getResult<GetStationList.ResponseBody>() }
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
    }

}