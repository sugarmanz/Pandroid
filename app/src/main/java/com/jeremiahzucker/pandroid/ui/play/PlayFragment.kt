package com.jeremiahzucker.pandroid.ui.play

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar

import com.jeremiahzucker.pandroid.R
import com.jeremiahzucker.pandroid.player.PlayMode
import com.jeremiahzucker.pandroid.player.PlayerInterface
import com.jeremiahzucker.pandroid.player.PlayerService
import com.jeremiahzucker.pandroid.request.json.v5.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.request.json.v5.model.TrackModel
import com.jeremiahzucker.pandroid.ui.main.MainActivity
import com.jeremiahzucker.pandroid.util.formatDurationFromMilliseconds
import com.jeremiahzucker.pandroid.util.formatDurationFromSeconds
import com.jeremiahzucker.pandroid.util.getCroppedBitmap
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.fragment_play.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PlayFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PlayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayFragment : Fragment(), PlayContract.View, PlayerInterface.Callback {

    override fun getContextForService() = activity.applicationContext

    private val TAG: String = PlayFragment::class.java.simpleName
    private val seekProgressHandler = Handler()
    private var player: PlayerInterface? = null
    private val presenter: PlayContract.Presenter = PlayPresenter
    private val albumTarget = object : Target {
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        override fun onBitmapFailed(errorDrawable: Drawable?) {}
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            if (bitmap != null) {
                image_view_album.cancelRotateAnimation()
                image_view_album.setImageBitmap(bitmap.getCroppedBitmap())
                if (player?.isPlaying == true)
                    image_view_album.startRotateAnimation()
            }
        }
    }

    // Need to use an actual Runnable object so that removeCallbacks will werk
    private val progressCallback = Runnable { updateSeekCallback() }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_play, container, false)
    }

    override fun onStart() {
        super.onStart()
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val scaledProgress = ((progress.toFloat() / seek_bar.max) * player!!.duration.toFloat()).toInt()
                    text_view_progress.text = scaledProgress.formatDurationFromMilliseconds()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                seekProgressHandler.removeCallbacks(progressCallback)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                player?.seekTo(getDurationFromProgress(seekBar.progress))
                if (player?.isPlaying == true) {
                    seekProgressHandler.removeCallbacks(progressCallback)
                    seekProgressHandler.post(progressCallback)
                }
            }
        })
        button_play_toggle.setOnClickListener {
            // If player is not null and player fails to pause and player fails to play,
            // show station list. This will work because player will return true if it is
            // able to pause or play correctly
            if (player != null && !player!!.pause() && !player!!.play()) {
                (activity as MainActivity).showStationList()
            }
        }
        button_play_mode_toggle.setOnClickListener {
            val playMode = PlayMode.nextMode(player?.playMode)
            player?.playMode = playMode
            updatePlayMode(playMode)
        }
        button_play_next.setOnClickListener { player?.playNext() }
        button_play_last.setOnClickListener { player?.playLast() }
        button_favorite_toggle.setOnClickListener {
            Log.i(TAG, player?.currentTrack?.feedbackId ?: "None")
            Log.i(TAG, player?.currentTrack?.songRating.toString())
            if (player?.currentTrack?.songRating == 1)
                presenter.removeTrackAsFavorite(player?.currentTrack?.feedbackId ?: "")
            else
                presenter.setTrackAsFavorite(player?.station?.stationToken ?: "", player?.currentTrack?.trackToken ?: "")
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)

        if (player != null) {
            updatePlayMode(player!!.playMode)
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.detach()
        seekProgressHandler.removeCallbacks(progressCallback)
    }

    override fun onPlayerServiceBound(service: PlayerService) {
        player = service
        player?.registerCallback(this)
    }

    override fun onPlayerServiceUnbound() {
        player?.unregisterCallback(this)
        player = null
    }

    override fun onTrackUpdated(track: TrackModel?) {
        if (track == null) {
            image_view_album.cancelRotateAnimation()
            button_play_toggle.setImageResource(R.drawable.ic_play)
            seek_bar.progress = 0
            text_view_progress.text = 0.formatDurationFromMilliseconds()
            player?.seekTo(0)
            seekProgressHandler.removeCallbacks(progressCallback)
        } else {
            // Step 1: Song name and artist
            text_view_song.text = track.songName
            text_view_artist.text = track.artistName
            // Step 2: favorite
            updateFavoriteToggle(track.songRating == 1)
            // Step 3: Duration
            text_view_duration.text = track.trackLength?.formatDurationFromSeconds()
            // Step 4: Keep these things updated
            // - Album rotation
            // - Progress(textViewProgress & seekBarProgress)
            image_view_album.pauseRotateAnimation()
            image_view_album.setImageResource(R.drawable.default_record_album)
            if (!track.albumArtUrl.isNullOrEmpty()) {
                Picasso.with(context).load(track.albumArtUrl).into(albumTarget)
            }

            seekProgressHandler.removeCallbacks(progressCallback)
            if (player?.isPlaying == true) {
                image_view_album.startRotateAnimation()
                seekProgressHandler.post(progressCallback)
                button_play_toggle.setImageResource(R.drawable.ic_pause)
            }
        }
    }

    override fun updateSeekCallback() {
        if (isDetached) return

        if (player != null && player?.isPlaying == true) {
            val progress: Int = (seek_bar.max * (player!!.progress.toFloat() / player!!.duration.toFloat())).toInt()
            text_view_progress.text = player!!.progress.formatDurationFromMilliseconds()
            if (progress >= 0 && progress <= seek_bar.max) {
                updateSeekProgress(progress, true)

                seekProgressHandler.postDelayed(progressCallback, UPDATE_PROGRESS_INTERVAL)
            }
        }
    }

    private fun updateSeekProgress(progress: Int, animate: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            seek_bar.setProgress(progress, animate)
        else
            seek_bar.progress = progress
    }

    override fun onTrackSetAsFavorite(favorite: Boolean, feedbackId: String?) {
        player?.currentTrack?.songRating = if (favorite) 1 else 0
        player?.currentTrack?.feedbackId = feedbackId

        // TODO: Update feedbacks in Player instance

        button_favorite_toggle.isEnabled = true
        updateFavoriteToggle(favorite)
    }

    override fun updatePlayMode(playMode: PlayMode) {
        button_play_mode_toggle.setImageResource(when (playMode) {
            PlayMode.SINGLE -> R.drawable.ic_play_mode_single
            PlayMode.LIST -> R.drawable.ic_play_mode_list
        })
    }

    override fun updatePlayToggle(play: Boolean) {
        button_play_toggle.setImageResource(if (play) R.drawable.ic_pause else R.drawable.ic_play)
    }

    override fun onSwitchLast(last: TrackModel?) {
        onTrackUpdated(last)
    }

    override fun updateFavoriteToggle(favorite: Boolean) {
        button_favorite_toggle.setImageResource(if (favorite) R.drawable.ic_favorite_yes else R.drawable.ic_favorite_no)
    }

    override fun onSwitchNext(next: TrackModel?) {
        onTrackUpdated(next)
    }

    override fun onComplete(next: TrackModel?) {
        onTrackUpdated(next)
    }

    override fun onPlayStatusChanged(isPlaying: Boolean) {
        updatePlayToggle(isPlaying)
        if (isPlaying) {
            image_view_album.resumeRotateAnimation()
            seekProgressHandler.removeCallbacks(progressCallback)
            seekProgressHandler.post(progressCallback)
        } else {
            image_view_album.pauseRotateAnimation()
            seekProgressHandler.removeCallbacks(progressCallback)
        }
    }

    fun setStation(station: ExpandedStationModel) {
        player?.station = station
    }

    private fun getDurationFromProgress(progress: Int) = ((player?.duration ?: 0) * (progress.toFloat() / seek_bar.max)).toInt()

    companion object {
        private val UPDATE_PROGRESS_INTERVAL = 1000L

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayFragment.
         */
        fun newInstance(): PlayFragment {
            val fragment = PlayFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
