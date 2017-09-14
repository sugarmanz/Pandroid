package com.jeremiahzucker.pandroid.ui.station

import com.jeremiahzucker.pandroid.ui.base.BasePresenter
import com.jeremiahzucker.pandroid.ui.base.BaseView
import com.jeremiahzucker.pandroid.request.method.exp.user.GetStationList
import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel

/**
 * StationListContract
 *
 * Author: Jeremiah Zucker
 * Date:   9/2/2017
 * Desc:   TODO: Complete
 */
interface StationListContract {

    interface View : BaseView<Presenter> {

        fun showProgress(show: Boolean)
        fun updateStationList(stations: List<ExpandedStationModel>)

    }

    interface Presenter : BasePresenter<View> {

        fun getStationList(body: GetStationList.RequestBody = GetStationList.RequestBody())

    }

}