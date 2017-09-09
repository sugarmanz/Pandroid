package com.jeremiahzucker.pandroid.ui.play

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jeremiahzucker.pandroid.R
import com.jeremiahzucker.pandroid.request.method.exp.station.GetPlaylist
import com.jeremiahzucker.pandroid.request.method.exp.user.GetStationList
import com.jeremiahzucker.pandroid.request.model.TrackModel
import com.jeremiahzucker.pandroid.ui.main.MainActivity
import com.jeremiahzucker.pandroid.util.formatDuration
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
class PlayFragment : Fragment(), PlayContract.View {

    private val seekProgressHandler = Handler()
    private val mediaPlayer = MediaPlayer()
    private var paused = false
    private var station: String? = null
    private var songs: List<TrackModel> = ArrayList(0)
    private var currentSong: TrackModel? = null

    override fun updateSeekProgress() {
        if (isDetached) return

        Log.i(TAG, "::updateSeekProgress")

        if (mediaPlayer.isPlaying) {
            val progress: Int = (seek_bar.max *(mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration.toFloat())).toInt()
            text_view_progress.text = mediaPlayer.currentPosition.formatDuration()
            if (progress >= 0 && progress <= seek_bar.max) {
                updateSeekProgress(progress, true)

                seekProgressHandler.postDelayed(this::updateSeekProgress, UPDATE_PROGRESS_INTERVAL)
            }
        }
    }

    fun updateSeekProgress(progress: Int, animate: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            seek_bar.setProgress(progress, animate)
        else
            seek_bar.progress = progress
    }

    private val TAG: String = PlayFragment::class.java.simpleName
    fun playSong(song: TrackModel) {
        Log.i(TAG, song.toString())

        currentSong = song
        text_view_song.text = song.songName
        text_view_artist.text = song.artistName
        text_view_duration.text = "00:00"
        image_view_album.setImageResource(R.drawable.default_record_album)

        if (!song.albumArtUrl.isNullOrEmpty()) {
            Picasso.with(context).load(song.albumArtUrl).into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                override fun onBitmapFailed(errorDrawable: Drawable?) {}
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    image_view_album.cancelRotateAnimation()
                    image_view_album.setImageBitmap(bitmap?.getCroppedBitmap())
                    if (mediaPlayer.isPlaying)
                        image_view_album.startRotateAnimation()
                }
            })
        }

//        if (mediaPlayer.isPlaying)
//            mediaPlayer.stop()
        paused = false
        mediaPlayer.reset()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setDataSource(song.audioUrlMap?.highQuality?.audioUrl)
        mediaPlayer.setOnCompletionListener {
            resetPlayer()
            onSongComplete()
        }
        mediaPlayer.setOnPreparedListener {
            text_view_duration.text = mediaPlayer.duration.formatDuration()
            playCurrentSong()
        }

        mediaPlayer.prepareAsync()
    }

    fun setStation(station: String) {
        this.station = station
        getMoreSongs()
    }

    fun getMoreSongs() {
        PlayPresenter().getPlaylist(GetPlaylist.RequestBody(station ?: "")) {
            Log.i(TAG, it.items.map { it.songName }.toString())
            playSongs(it.items.filter { it.trackToken != null })
        }
    }

    fun playNextSong() {
        resetPlayer()
        onSongComplete()
    }

    fun playSongFromBeginning() {
        image_view_album.cancelRotateAnimation()
        mediaPlayer.seekTo(0)
        updateSeekProgress(0, false)
        image_view_album.startRotateAnimation()
    }

    private fun resetPlayer() {
        seekProgressHandler.removeCallbacks(this::updateSeekProgress)
        image_view_album.cancelRotateAnimation()
        mediaPlayer.reset()
    }

    private fun playCurrentSong() {
        mediaPlayer.start()
        image_view_album.resumeRotateAnimation()
        seekProgressHandler.post(this::updateSeekProgress)
        button_play_toggle.setImageResource(R.drawable.ic_pause)
        paused = false
    }

    private fun pauseCurrentSong() {
        mediaPlayer.pause()
        image_view_album.pauseRotateAnimation()
        seekProgressHandler.removeCallbacks(this::updateSeekProgress)
        button_play_toggle.setImageResource(R.drawable.ic_play)
        paused = true
    }

    fun onSongComplete() {
        if (songs.isEmpty())
            getMoreSongs()
        else {
            val song = songs[0]
            songs = songs.subList(1, songs.size)
            playSong(song)
        }
    }

    fun playSongs(songs: List<TrackModel>) {
        val song = songs[0]
        this.songs = songs.subList(1, songs.size)
        playSong(song)
    }

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_play, container, false)

        view.findViewById(R.id.button_play_toggle).setOnClickListener {
            when {
                mediaPlayer.isPlaying -> pauseCurrentSong()
                paused -> playCurrentSong()
                else -> (activity as MainActivity).showStationList()
            }
        }

        view.findViewById(R.id.button_play_next).setOnClickListener { playNextSong() }
        view.findViewById(R.id.button_play_last).setOnClickListener { playSongFromBeginning() }

        return view
    }

    override fun onPause() {
        super.onPause()
        seekProgressHandler.removeCallbacks(this::updateSeekProgress)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

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
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): PlayFragment {
            val fragment = PlayFragment()
            val args = Bundle()
//            args.putString(ARG_PARAM1, param1)
//            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
