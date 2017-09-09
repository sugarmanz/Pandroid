package com.jeremiahzucker.pandroid.ui.play

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
    override fun play(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun play(station: ExpandedStationModel): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun attach(view: PlayContract.View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun detach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val TAG = PlayPresenter::class.java.simpleName

    fun getPlaylist(body: GetPlaylist.RequestBody, success: (GetPlaylist.ResponseBody) -> Unit) {
        Pandora(Pandora.Protocol.HTTP)
                .RequestBuilder(GetPlaylist)
                .body(body)
                .buildObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { it.isOk }
                .map { it.getResult<GetPlaylist.ResponseBody>() }
                .subscribe(success)
    }

}