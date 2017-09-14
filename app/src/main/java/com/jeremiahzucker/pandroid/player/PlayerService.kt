package com.jeremiahzucker.pandroid.player

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v7.app.NotificationCompat
import android.widget.RemoteViews
import com.jeremiahzucker.pandroid.R
import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.request.model.TrackModel
import com.jeremiahzucker.pandroid.ui.main.MainActivity
import com.squareup.picasso.Picasso

/**
 * Created by sugarmanz on 9/10/17.
 */
class PlayerService : Service(), PlayerInterface, PlayerInterface.Callback {

    private var notification: Notification? = null
    private var mContentViewBig: RemoteViews? = null
    private var mContentViewSmall: RemoteViews? = null

    private val mBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        val service: PlayerService get() = this@PlayerService
    }

    override fun onCreate() {
        super.onCreate()
        Player.registerCallback(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.action
            if (ACTION_PLAY_TOGGLE == action) {
                if (isPlaying) {
                    pause()
                } else {
                    play()
                }
            } else if (ACTION_PLAY_NEXT == action) {
                playNext()
            } else if (ACTION_PLAY_LAST == action) {
                playLast()
            } else if (ACTION_STOP_SERVICE == action) {
                if (isPlaying) {
                    pause()
                }
                stopForeground(true)
                unregisterCallback(this)
            }
        }
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    override fun stopService(name: Intent): Boolean {
        stopForeground(true)
        unregisterCallback(this)
        return super.stopService(name)
    }

    override fun onDestroy() {
        releasePlayer()
        super.onDestroy()
    }

    override var station: ExpandedStationModel?
        get() = Player.station
        set(value) {
            Player.station = value
        }

    override var playMode: PlayMode
        get() = Player.playMode
        set(value) {
            Player.playMode = value
        }

    override fun play() = Player.play()

    override fun play(list: ExpandedStationModel) = Player.play(list)

    override fun play(track: TrackModel) = Player.play(track)

    override fun playLast() = Player.playLast()

    override fun playNext() = Player.playNext()

    override fun pause() = Player.pause()

    override val isPlaying get() = Player.isPlaying

    override val progress get() = Player.progress

    override val duration get() = Player.duration

    override val currentTrack get() = Player.currentTrack

    override fun seekTo(progress: Int) = Player.seekTo(progress)

    override fun registerCallback(callback: PlayerInterface.Callback) = Player.registerCallback(callback)

    override fun unregisterCallback(callback: PlayerInterface.Callback) = Player.unregisterCallback(callback)

    override fun removeCallbacks() = Player.removeCallbacks()

    override fun releasePlayer() {
        Player.releasePlayer()
        super.onDestroy()
    }

    // Playback Callbacks
    override fun onSwitchLast(last: TrackModel?) = showNotification()
    override fun onSwitchNext(next: TrackModel?) = showNotification()
    override fun onComplete(next: TrackModel?) = showNotification()
    override fun onPlayStatusChanged(isPlaying: Boolean) = showNotification()

    // Notification

    /**
     * Show a notification while this service is running.
     */
    private fun showNotification() {
        // The PendingIntent to launch our activity if the user selects this notification
        val contentIntent = PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), 0)

        // Cache content views for album art
        val smallRemoteView = smallContentView
        val bigRemoteView = bigContentView

        // Set the info for the views that show in the notification panel.
        notification = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification_app_logo)  // the status icon
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .setCustomContentView(smallRemoteView)
                .setCustomBigContentView(bigRemoteView)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(true)
                .build()

        val albumArtUrl = currentTrack?.albumArtUrl ?: ""
        if (!albumArtUrl.isNullOrEmpty()) {
            Picasso.with(applicationContext).load(albumArtUrl)
                    .into(smallRemoteView, R.id.image_view_album, NOTIFICATION_ID, notification)
            Picasso.with(applicationContext).load(albumArtUrl) // TODO: Hopefully caching is smert
                    .into(bigRemoteView, R.id.image_view_album, NOTIFICATION_ID, notification)
        }

        // Send the notification.
        startForeground(NOTIFICATION_ID, notification)
    }

    private val smallContentView: RemoteViews
        get() {
            if (mContentViewSmall == null) {
                mContentViewSmall = RemoteViews(packageName, R.layout.remote_view_music_player_small)
                setUpRemoteView(mContentViewSmall!!)
            }
            updateRemoteViews(mContentViewSmall!!)
            return mContentViewSmall!!
        }

    private val bigContentView: RemoteViews
        get() {
            if (mContentViewBig == null) {
                mContentViewBig = RemoteViews(packageName, R.layout.remote_view_music_player)
                setUpRemoteView(mContentViewBig!!)
            }
            updateRemoteViews(mContentViewBig!!)
            return mContentViewBig!!
        }

    private fun setUpRemoteView(remoteView: RemoteViews) {
        remoteView.setImageViewResource(R.id.image_view_close, R.drawable.ic_remote_view_close)
        remoteView.setImageViewResource(R.id.image_view_play_last, R.drawable.ic_remote_view_play_last)
        remoteView.setImageViewResource(R.id.image_view_play_next, R.drawable.ic_remote_view_play_next)

        remoteView.setOnClickPendingIntent(R.id.button_close, getPendingIntent(ACTION_STOP_SERVICE))
        remoteView.setOnClickPendingIntent(R.id.button_play_last, getPendingIntent(ACTION_PLAY_LAST))
        remoteView.setOnClickPendingIntent(R.id.button_play_next, getPendingIntent(ACTION_PLAY_NEXT))
        remoteView.setOnClickPendingIntent(R.id.button_play_toggle, getPendingIntent(ACTION_PLAY_TOGGLE))
    }

    private fun updateRemoteViews(remoteView: RemoteViews) {
        val currentTrack = Player.currentTrack
        if (currentTrack != null) {
            remoteView.setTextViewText(R.id.text_view_name, currentTrack.songName)
            remoteView.setTextViewText(R.id.text_view_artist, currentTrack.artistName)

            remoteView.setImageViewResource(R.id.image_view_album, R.mipmap.ic_launcher)
//            if (!currentTrack.albumArtUrl.isNullOrEmpty()) {
//                Picasso.with(applicationContext).load(currentTrack.albumArtUrl)
//                        .into(remoteView, R.id.image_view_album, NOTIFICATION_ID, notification)
//            }
        }
        remoteView.setImageViewResource(R.id.image_view_play_toggle,
                if (isPlaying) R.drawable.ic_remote_view_pause else R.drawable.ic_remote_view_play)

        // TODO: Do album cover loading

    }

    // PendingIntent

    private fun getPendingIntent(action: String): PendingIntent {
        return PendingIntent.getService(this, 0, Intent(action), 0)
    }

    companion object {

        private val ACTION_PLAY_TOGGLE = "com.jeremiahzucker.pandroid.player.ACTION.PLAY_TOGGLE"
        private val ACTION_PLAY_LAST = "com.jeremiahzucker.pandroid.player.ACTION.PLAY_LAST"
        private val ACTION_PLAY_NEXT = "com.jeremiahzucker.pandroid.player.ACTION.PLAY_NEXT"
        private val ACTION_STOP_SERVICE = "com.jeremiahzucker.pandroid.player.ACTION.STOP_SERVICE"

        private val NOTIFICATION_ID = 1
    }
}
