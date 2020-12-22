package com.jeremiahzucker.pandroid.ui.play

import android.content.Context
import com.jeremiahzucker.pandroid.models.TrackModel
import com.jeremiahzucker.pandroid.player.PlayMode
import com.jeremiahzucker.pandroid.player.PlayerService
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

        fun updateSeekCallback()

        fun registerWithPlayerService(service: PlayerService)

        fun unregisterWithPlayerService()

        fun onTrackSetAsFavorite(favorite: Boolean, feedbackId: String?)

        fun onTrackUpdated(track: TrackModel?)

        fun updatePlayMode(playMode: PlayMode)

        fun updatePlayToggle(play: Boolean)

        fun updateFavoriteToggle(favorite: Boolean)

        fun getContextForService(): Context
    }

    interface Presenter : BasePresenter<View> {

        // TODO: Convert to thumbs up/down terminology
        fun setTrackAsFavorite(stationToken: String, trackToken: String)

        fun removeTrackAsFavorite(feedbackId: String)

        fun bindPlayerService()

        fun unbindPlayerService()
    }
}
