package com.jeremiahzucker.pandroid.ui.main

import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.ui.base.BasePresenter
import com.jeremiahzucker.pandroid.ui.base.BaseView

/**
 * MainContract
 *
 * Author: Jeremiah Zucker
 * Date:   9/4/2017
 * Desc:   TODO: Complete
 */
interface MainContract {

    interface View : BaseView<Presenter> {

        fun showStationList()
        fun showPlayer(station: ExpandedStationModel? = null)
        fun showSettings()

    }

    interface Presenter : BasePresenter<View> {


        fun onStationClicked()

    }

}