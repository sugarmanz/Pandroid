package com.jeremiahzucker.pandroid.ui.play

import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.ui.base.BasePresenter
import com.jeremiahzucker.pandroid.ui.base.BaseView

/**
 * PlayContract
 *
 * Author: Jeremiah Zucker
 * Date:   9/2/2017
 * Desc:   TODO: Complete
 */
interface PlayContract {

    interface View : BaseView<Presenter> {

        fun updateSeekProgress()

    }

    interface Presenter : BasePresenter<View> {

        fun play(): Boolean

        fun play(station: ExpandedStationModel): Boolean

        fun pause(): Boolean

    }

}