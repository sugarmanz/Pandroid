package com.jeremiahzucker.pandroid.player

import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.request.model.SongSeedModel

/**
 * PlaybackInterface
 *
 * Author: Jeremiah Zucker
 * Date:   9/4/2017
 * Desc:   TODO: Complete
 */
interface PlaybackInterface {

    fun setStation(station: ExpandedStationModel)

    fun play(): Boolean

    fun play(station: ExpandedStationModel): Boolean

    fun play(station: ExpandedStationModel, startIndex: Int): Boolean

    fun play(song: SongSeedModel): Boolean

    fun playLast(): Boolean

    fun playNext(): Boolean

    fun pause(): Boolean

    val isPlaying: Boolean

    val progress: Int

    val playingSong: SongSeedModel

    fun seekTo(progress: Int): Boolean

    fun setPlayMode(playMode: PlayMode)

    fun registerCallback(callback: Callback)

    fun unregisterCallback(callback: Callback)

    fun removeCallbacks()

    fun releasePlayer()

    interface Callback {

        fun onSwitchLast(last: SongSeedModel?)

        fun onSwitchNext(next: SongSeedModel?)

        fun onComplete(next: SongSeedModel?)

        fun onPlayStatusChanged(isPlaying: Boolean)
    }
}
