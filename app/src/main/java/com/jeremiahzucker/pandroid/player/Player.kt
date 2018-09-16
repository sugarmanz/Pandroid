package com.jeremiahzucker.pandroid.player

import android.media.MediaPlayer
import android.util.Log
import com.jeremiahzucker.pandroid.request.Pandora
import com.jeremiahzucker.pandroid.request.json.v5.method.station.GetPlaylist
import com.jeremiahzucker.pandroid.request.json.v5.method.station.GetStation
import com.jeremiahzucker.pandroid.request.json.v5.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.request.json.v5.model.FeedbackModel
import com.jeremiahzucker.pandroid.request.json.v5.model.TrackModel
import kotlin.reflect.KProperty

/**
 * Created by sugarmanz on 9/10/17.
 */
internal object Player : PlayerInterface, MediaPlayer.OnCompletionListener {

    private val TAG: String = Player::class.java.simpleName
    // TODO: Consider faster alternatives to MediaPlayer
    // At the moment, I am considering simply downloading the mp3 files
    // and using the MediaPlayer to play them. This will hopefully solve
    // the issues with prepareAsync being super slow and will allow for
    // offline use.
    private var mediaPlayer = MediaPlayer()
    private val callbacks = ArrayList<PlayerInterface.Callback>()
    private val tracks = ArrayList<TrackModel>()
    private val feedbacks = mutableMapOf<String, FeedbackModel>()
    private var index = 0
    private val hasNext get() = index < tracks.size - 1
    private val hasLast get() = tracks.isNotEmpty()
    private val nextTrack get() = tracks[++index]
    private val lastTrack get() = tracks[if (index - 1 < 0) index else --index]
    private var isPaused = false

    override var playMode: PlayMode = PlayMode.default

    override var station: ExpandedStationModel? = null
        set(value) {
            // Only bother setting if changing stations
            if (field?.stationToken != value?.stationToken) {
                field = value
                // Reset playlist
                clearPlaylist()
                // Load feedbacks first
                loadFeedback()
                // Load initial playlist
                loadPlaylist()
            }
        }

    override var currentTrack: TrackModel? = null
        private set

    override val isPlaying: Boolean by HandleIllegalStateDelegate(false) { mediaPlayer.isPlaying }
    override val progress: Int by HandleIllegalStateDelegate(0) { mediaPlayer.currentPosition }
    override val duration: Int by HandleIllegalStateDelegate(0) { mediaPlayer.duration }

    internal class HandleIllegalStateDelegate<out T>(private val defaultValue: T, private val getter: () -> T) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return try {
                getter()
            } catch (e: IllegalStateException) {
                mediaPlayer.release()
                mediaPlayer = MediaPlayer()
                defaultValue
            }
        }
    }

    init {
        mediaPlayer.setOnCompletionListener(this)
    }

    // IMPORTANT: Assumes that currentTrack has been set already
    override fun play(): Boolean {
        if (isPaused) {
            isPaused = false
            mediaPlayer.start()
            notifyPlayStatusChanged(true)
            return true
        } else if (currentTrack != null) {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(currentTrack?.audioUrlMap?.highQuality?.audioUrl)
            mediaPlayer.prepare() // TODO: PrepareAsync?????
            mediaPlayer.start()
            notifyPlayStatusChanged(true)

            if (!hasNext)
                loadPlaylist()
            return true
        }
        return false
    }

    override fun play(station: ExpandedStationModel): Boolean {
        isPaused = false
        this.station = station
        return true // Not really, lol
    }

    // Will add the track as the next track and then play it
    override fun play(track: TrackModel): Boolean {
        isPaused = false
        return if (tracks.isNotEmpty()) {
            tracks.add(index + 1, track)
            playNext()
        } else {
            tracks.add(track)
            play()
        }
    }

    override fun playLast(): Boolean {
        isPaused = false
        if (hasLast) {
            currentTrack = lastTrack
            play()
            notifyPlayLast(currentTrack as TrackModel)
            return true
        }
        return false
    }

    override fun playNext(): Boolean {
        isPaused = false
        if (hasNext) {
            currentTrack = nextTrack
            play()
            notifyPlayNext(currentTrack as TrackModel)
            return true
        }
        return false
    }

    override fun pause(): Boolean {
        if (isPlaying) {
            mediaPlayer.pause()
            isPaused = true
            notifyPlayStatusChanged(false)
            return true
        }
        return false
    }

    override fun seekTo(progress: Int): Boolean {
        if (currentTrack != null) {
            if (duration <= progress) {
                onCompletion(mediaPlayer)
            } else {
                mediaPlayer.seekTo(progress)
            }
            return true
        }
        return false
    }

    override fun registerCallback(callback: PlayerInterface.Callback) {
        callbacks.add(callback)
    }

    override fun unregisterCallback(callback: PlayerInterface.Callback) {
        callbacks.remove(callback)
    }

    override fun removeCallbacks() {
        callbacks.clear()
    }

    override fun releasePlayer() {
        mediaPlayer.release()
    }

    private fun notifyPlayStatusChanged(isPlaying: Boolean) {
        callbacks.forEach { it.onPlayStatusChanged(isPlaying) }
    }

    private fun notifyPlayLast(track: TrackModel) {
        callbacks.forEach { it.onSwitchLast(track) }
    }

    private fun notifyPlayNext(track: TrackModel) {
        callbacks.forEach { it.onSwitchNext(track) }
    }

    private fun notifyComplete(track: TrackModel?) {
        callbacks.forEach { it.onComplete(track) }
    }

    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        var next: TrackModel? = null
        // There is only one limited play mode which is list, player should be stopped when hitting the list end
        if (playMode === PlayMode.LIST && index == tracks.size - 1) {
            // In the end of the list
            // Do nothing, just deliver the callback
        } else if (playMode === PlayMode.SINGLE) {
            currentTrack = currentTrack
            play()
        } else if (hasNext) {
            currentTrack = nextTrack
            play()
        }
        notifyComplete(currentTrack)
    }

    private fun clearPlaylist() {
        mediaPlayer.reset()
        index = 0
        tracks.clear()
        feedbacks.clear()
        currentTrack = null
    }

    private fun loadPlaylist() = Pandora.HTTP
            .RequestBuilder(GetPlaylist)
            .body(GetPlaylist.RequestBody(station?.stationToken ?: ""))
            .build<GetPlaylist.ResponseBody>()
            .subscribe(this::loadPlaylistSuccess, this::loadPlaylistError)

    private fun loadPlaylistSuccess(response: GetPlaylist.ResponseBody) {
        // Add feedback id if it exists
        response.items.forEach {
            it.feedbackId = feedbacks[it.trackToken]?.feedbackId

        }

        // Add the tracks to the queue
        tracks.addAll(response.items.filter { it.trackToken != null })
        if (currentTrack == null) {
            index = 0
            currentTrack = tracks[0]
            play()
            notifyComplete(currentTrack)
        }
    }
    private fun loadPlaylistError(throwable: Throwable) {
        throwable.printStackTrace()
    }

    private fun loadFeedback() = Pandora.HTTPS
            .RequestBuilder(GetStation)
            .body(GetStation.RequestBody(station?.stationToken ?: "", true))
            .build<GetStation.ResponseBody>()
            .subscribe(this::loadFeedbackSuccess, this::loadFeedbackError)

    private fun loadFeedbackSuccess(response: GetStation.ResponseBody) {
        response.feedback.thumbsUp.associateBy { it.musicToken }
    }
    private fun loadFeedbackError(throwable: Throwable) {
        throwable.printStackTrace()
    }


}