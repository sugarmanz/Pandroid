package com.jeremiahzucker.pandroid.player

import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.request.model.TrackModel

/**
 * Created by sugarmanz on 9/10/17.
 */
interface PlayerInterface {

    val isPlaying: Boolean

    val progress: Int

    val duration: Int

    val currentTrack: TrackModel?

    var station: ExpandedStationModel?

    var playMode: PlayMode

    fun play(): Boolean

    fun play(station: ExpandedStationModel): Boolean

    // TODO: See if this should be included
    fun play(song: TrackModel): Boolean

    fun playLast(): Boolean

    fun playNext(): Boolean

    fun pause(): Boolean

    fun seekTo(progress: Int): Boolean

    fun registerCallback(callback: Callback)

    fun unregisterCallback(callback: Callback)

    fun removeCallbacks()

    fun releasePlayer()

    interface Callback {

        fun onSwitchLast(last: TrackModel?)

        fun onSwitchNext(next: TrackModel?)

        fun onComplete(next: TrackModel?)

        fun onPlayStatusChanged(isPlaying: Boolean)
    }
}
