package com.jeremiahzucker.pandroid.player

import android.media.MediaPlayer
import android.util.Log
import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.request.model.StationModel
import java.io.IOException
import java.util.ArrayList

/**
 * Player
 *
 * Author: Jeremiah Zucker
 * Date:   9/4/2017
 * Desc:   TODO: Complete
 */
class Player private constructor() : PlaybackInterface, MediaPlayer.OnCompletionListener {

    private var mPlayer: MediaPlayer? = null

    private var mStation: StationModel? = null
    // Default size 2: for service and UI
    private val mCallbacks = ArrayList<PlaybackInterface.Callback>(2)

    // Player status
    private var isPaused: Boolean = false

    init {
        mPlayer = MediaPlayer()
        mStation = PlayList()
        mPlayer!!.setOnCompletionListener(this)
    }

    fun setStation(list: StationModel?) {
        var list = list
        if (list == null) {
            list = PlayList()
        }
        mStation = list
    }

    fun play(): Boolean {
        if (isPaused) {
            mPlayer!!.start()
            notifyPlayStatusChanged(true)
            return true
        }
        if (mStation!!.prepare()) {
            val song = mStation!!.getCurrentSong()
            try {
                mPlayer!!.reset()
                mPlayer!!.setDataSource(song.getPath())
                mPlayer!!.prepare()
                mPlayer!!.start()
                notifyPlayStatusChanged(true)
            } catch (e: IOException) {
                Log.e(TAG, "play: ", e)
                notifyPlayStatusChanged(false)
                return false
            }

            return true
        }
        return false
    }

    fun play(list: PlayList?): Boolean {
        if (list == null) return false

        isPaused = false
        setStation(list)
        return play()
    }

    override fun play(station: ExpandedStationModel?, startIndex: Int): Boolean {
        if (list == null || startIndex < 0 || startIndex >= list!!.getNumOfSongs()) return false

        isPaused = false
        list!!.setPlayingIndex(startIndex)
        setStation(list)
        return play()
    }

    fun play(song: Song?): Boolean {
        if (song == null) return false

        isPaused = false
        mStation!!.getSongs().clear()
        mStation!!.getSongs().add(song)
        return play()
    }

    fun playLast(): Boolean {
        isPaused = false
        val hasLast = mStation!!.hasLast()
        if (hasLast) {
            val last = mStation!!.last()
            play()
            notifyPlayLast(last)
            return true
        }
        return false
    }

    fun playNext(): Boolean {
        isPaused = false
        val hasNext = mStation!!.hasNext(false)
        if (hasNext) {
            val next = mStation!!.next()
            play()
            notifyPlayNext(next)
            return true
        }
        return false
    }

    fun pause(): Boolean {
        if (mPlayer!!.isPlaying) {
            mPlayer!!.pause()
            isPaused = true
            notifyPlayStatusChanged(false)
            return true
        }
        return false
    }

    val isPlaying: Boolean
        get() = mPlayer!!.isPlaying

    val progress: Int
        get() = mPlayer!!.currentPosition

    val playingSong: Song?
        get() = mStation!!.getCurrentSong()

    fun seekTo(progress: Int): Boolean {
        if (mStation!!.getSongs().isEmpty()) return false

        val currentSong = mStation!!.getCurrentSong()
        if (currentSong != null) {
            if (currentSong!!.getDuration() <= progress) {
                onCompletion(mPlayer)
            } else {
                mPlayer!!.seekTo(progress)
            }
            return true
        }
        return false
    }

    fun setPlayMode(playMode: PlayMode) {
        mStation!!.setPlayMode(playMode)
    }

    // Listeners

    override fun onCompletion(mp: MediaPlayer?) {
        var next: Song? = null
        // There is only one limited play mode which is list, player should be stopped when hitting the list end
        if (mStation!!.getPlayMode() === PlayMode.LIST && mStation!!.getPlayingIndex() === mPlayList!!.getNumOfSongs() - 1) {
            // In the end of the list
            // Do nothing, just deliver the callback
        } else if (mStation!!.getPlayMode() === PlayMode.SINGLE) {
            next = mStation!!.getCurrentSong()
            play()
        } else {
            val hasNext = mStation!!.hasNext(true)
            if (hasNext) {
                next = mStation!!.next()
                play()
            }
        }
        notifyComplete(next)
    }

    fun releasePlayer() {
        mStation = null
        mPlayer!!.reset()
        mPlayer!!.release()
        mPlayer = null
        sInstance = null
    }

    // Callbacks

    fun registerCallback(callback: Callback) {
        mCallbacks.add(callback)
    }

    fun unregisterCallback(callback: Callback) {
        mCallbacks.remove(callback)
    }

    fun removeCallbacks() {
        mCallbacks.clear()
    }

    private fun notifyPlayStatusChanged(isPlaying: Boolean) {
        for (callback in mCallbacks) {
            callback.onPlayStatusChanged(isPlaying)
        }
    }

    private fun notifyPlayLast(song: Song) {
        for (callback in mCallbacks) {
            callback.onSwitchLast(song)
        }
    }

    private fun notifyPlayNext(song: Song) {
        for (callback in mCallbacks) {
            callback.onSwitchNext(song)
        }
    }

    private fun notifyComplete(song: Song?) {
        for (callback in mCallbacks) {
            callback.onComplete(song)
        }
    }

    companion object {

        private val TAG = "Player"

        @Volatile private var sInstance: Player? = null

        val instance: Player?
            get() {
                if (sInstance == null) {
                    synchronized(Player::class.java) {
                        if (sInstance == null) {
                            sInstance = Player()
                        }
                    }
                }
                return sInstance
            }
    }
}
