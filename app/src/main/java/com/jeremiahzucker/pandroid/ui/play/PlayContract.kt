package com.jeremiahzucker.pandroid.ui.play

import com.jeremiahzucker.pandroid.player.PlayerService
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

        fun onPlaybackServiceBound(service: PlayerService)

        fun onPlaybackServiceUnbound()

    }

    interface Presenter : BasePresenter<View> {

        fun loadPlaylist(stationToken: String)

    }

}