package com.jeremiahzucker.pandroid.ui.station

import com.jeremiahzucker.pandroid.request.json.v5.method.user.GetStationList
import com.jeremiahzucker.pandroid.request.json.v5.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.ui.base.BasePresenter
import com.jeremiahzucker.pandroid.ui.base.BaseView

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

        fun showAuth()
    }

    interface Presenter : BasePresenter<View> {

        fun getStationList(body: GetStationList.RequestBody = GetStationList.RequestBody())
    }
}
