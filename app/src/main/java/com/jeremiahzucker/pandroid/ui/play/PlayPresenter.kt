package com.jeremiahzucker.pandroid.ui.play

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.jeremiahzucker.pandroid.player.PlayerService
import com.jeremiahzucker.pandroid.request.model.TrackModel

/**
 * Created by Jeremiah Zucker on 8/25/2017.
 */
object PlayPresenter : PlayContract.Presenter {

    private val TAG = PlayPresenter::class.java.simpleName

    private var view: PlayContract.View? = null
    private var playerService: PlayerService? = null
    private var serviceBound: Boolean = false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            playerService = (service as PlayerService.LocalBinder).service
            view?.onPlayerServiceBound(playerService!!)
            view?.onTrackUpdated(playerService!!.currentTrack)
        }

        override fun onServiceDisconnected(className: ComponentName) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            playerService = null
            view?.onPlayerServiceUnbound()
        }
    }

    override fun attach(view: PlayContract.View) {
        this.view = view

        bindPlayerService()

        // TODO
        if (playerService != null && playerService!!.isPlaying) {
            view.onTrackUpdated(playerService!!.currentTrack)
        } else {
            // - load last play list/folder/song
        }
    }

    override fun detach() {
        unbindPlayerService()
        // Release context reference
//        context = null
        view = null
    }

    override fun setTrackAsFavorite(track: TrackModel, favorite: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bindPlayerService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        view?.getContextForService()?.bindService(Intent(view?.getContextForService(), PlayerService::class.java), connection, Context.BIND_AUTO_CREATE)
        serviceBound = true
    }

    override fun unbindPlayerService() {
        if (serviceBound) {
            // Detach our existing connection.
            view?.getContextForService()?.unbindService(connection)
            serviceBound = false
        }
    }

}