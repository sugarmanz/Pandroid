package com.jeremiahzucker.pandroid.ui.station

import com.jeremiahzucker.pandroid.models.ExpandedStationModel
import com.jeremiahzucker.pandroid.ui.base.BasePresenter
import com.jeremiahzucker.pandroid.ui.base.BaseView

interface StationListContract {

    interface View : BaseView<Presenter> {

        fun showProgress(show: Boolean)

        fun showAuth()
    }

    interface Presenter : BasePresenter<View> {
        suspend fun getStations(): List<ExpandedStationModel>
    }
}
