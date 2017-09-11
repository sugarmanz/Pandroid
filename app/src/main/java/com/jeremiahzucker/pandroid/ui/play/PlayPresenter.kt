package com.jeremiahzucker.pandroid.ui.play

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.jeremiahzucker.pandroid.player.PlayerService
import com.jeremiahzucker.pandroid.request.BasicCallback
import com.jeremiahzucker.pandroid.request.Pandora
import com.jeremiahzucker.pandroid.request.method.exp.station.GetPlaylist
import com.jeremiahzucker.pandroid.request.method.exp.user.GetStationList
import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel
import com.jeremiahzucker.pandroid.request.model.ResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call

/**
 * Created by Jeremiah Zucker on 8/25/2017.
 */
class PlayPresenter : PlayContract.Presenter {

    private var view: PlayContract.View? = null
    private var playerService: PlayerService? = null
    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            playerService = (service as PlayerService.LocalBinder).service
            view?.onPlaybackServiceBound(playerService!!)
            view?.onSongUpdated(playerService.getPlayingSong())
        }

        override fun onServiceDisconnected(className: ComponentName) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            playerService = null
            view?.onPlaybackServiceUnbound()
        }
    }

    override fun attach(view: PlayContract.View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun detach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val TAG = PlayPresenter::class.java.simpleName

    override fun loadPlaylist(stationToken: String) {
        Pandora(Pandora.Protocol.HTTP)
                .RequestBuilder(GetPlaylist)
                .body(GetPlaylist.RequestBody(stationToken))
                .buildObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { it.isOk }
                .map { it.getResult<GetPlaylist.ResponseBody>() }
                .subscribe(this::loadPlaylistSuccess, this::loadPlaylistError)
    }

    private fun loadPlaylistSuccess(response: GetPlaylist.ResponseBody) {
        // TODO UR MOM
    }

    private fun loadPlaylistError(error: Throwable) {
        // TODO U SUK
    }

}