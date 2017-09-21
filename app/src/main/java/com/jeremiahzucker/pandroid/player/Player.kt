package com.jeremiahzucker.pandroid.player

import android.media.MediaPlayer
import com.jeremiahzucker.pandroid.request.Pandora
import com.jeremiahzucker.pandroid.request.method.exp.station.GetPlaylist
import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.request.model.TrackModel

/**
 * Created by sugarmanz on 9/10/17.
 */
internal object Player : PlayerInterface, MediaPlayer.OnCompletionListener {

    private val TAG: String = Player::class.java.simpleName
    // TODO: Consider faster alternatives to MediaPlayer
    private val mediaPlayer = MediaPlayer()
    private val callbacks = ArrayList<PlayerInterface.Callback>()
    private val tracks = ArrayList<TrackModel>()
    private var index = 0
    private val hasNext get() = index < tracks.size - 1
    private val hasLast get() = tracks.isNotEmpty()
    private val nextTrack get() = tracks[++index]
    private val lastTrack get() = tracks[if (index - 1 < 0) index else --index]
    private var isPaused = false

    override var playMode: PlayMode = PlayMode.default

    override var station: ExpandedStationModel? = null
        set(value) {
            if (field?.stationToken != value?.stationToken) {
                field = value
                clearPlaylist()
                loadPlaylist()
            }
        }

    override var currentTrack: TrackModel? = null
        private set

    override val isPlaying get() = mediaPlayer.isPlaying
    override val progress get() = mediaPlayer.currentPosition
    override val duration get() = mediaPlayer.duration

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

    // Will add the song as the next track and then play it
    override fun play(song: TrackModel): Boolean {
        isPaused = false
        return if (tracks.isNotEmpty()) {
            tracks.add(index + 1, song)
            playNext()
        } else {
            tracks.add(song)
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
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            isPaused = true
            notifyPlayStatusChanged(false)
            return true
        }
        return false
    }

    override fun seekTo(progress: Int): Boolean {
        if (currentTrack != null) {
            if (mediaPlayer.duration <= progress) {
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
//        mediaPlayer.reset()
        mediaPlayer.release()
    }

    private fun notifyPlayStatusChanged(isPlaying: Boolean) {
        for (callback in callbacks) {
            callback.onPlayStatusChanged(isPlaying)
        }
    }

    private fun notifyPlayLast(song: TrackModel) {
        for (callback in callbacks) {
            callback.onSwitchLast(song)
        }
    }

    private fun notifyPlayNext(song: TrackModel) {
        for (callback in callbacks) {
            callback.onSwitchNext(song)
        }
    }

    private fun notifyComplete(song: TrackModel?) {
        for (callback in callbacks) {
            callback.onComplete(song)
        }
    }

    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        var next: TrackModel? = null
        // There is only one limited play mode which is list, player should be stopped when hitting the list end
        if (playMode === PlayMode.LIST && index == tracks.size - 1) {
            // In the end of the list
            // Do nothing, just deliver the callback
        } else if (playMode === PlayMode.SINGLE) {
            next = currentTrack
            play()
        } else if (hasNext) {
            next = nextTrack
            play()
        }
        notifyComplete(next)
    }

    private fun clearPlaylist() {
        mediaPlayer.reset()
        index = 0
        tracks.clear()
        currentTrack = null
    }

    private fun loadPlaylist() = Pandora
            .RequestBuilder(GetPlaylist)
            .protocol(Pandora.Protocol.HTTP)
            .body(GetPlaylist.RequestBody(station?.stationToken ?: ""))
            .build<GetPlaylist.ResponseBody>()
            .subscribe(this::loadPlaylistSuccess, this::loadPlaylistError)

    private fun loadPlaylistSuccess(response: GetPlaylist.ResponseBody) {
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

}