package com.jeremiahzucker.pandroid.ui.search

import android.util.Log
import com.google.gson.JsonElement
import com.jeremiahzucker.pandroid.Preferences
import com.jeremiahzucker.pandroid.player.Player
import com.jeremiahzucker.pandroid.request.Pandora
import com.jeremiahzucker.pandroid.request.method.exp.music.Search
import com.jeremiahzucker.pandroid.request.method.exp.station.CreateStation
import com.jeremiahzucker.pandroid.request.method.exp.user.GetStationList
import com.jeremiahzucker.pandroid.request.method.exp.user.GetStationListChecksum
import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel
import io.realm.Realm

/**
 * SearchPresenter
 *
 * Author: Jeremiah Zucker
 * Date:   9/2/2017
 * Desc:   TODO: Complete
 */
class SearchPresenter : SearchContract.Presenter {

    private val TAG: String = SearchPresenter::class.java.simpleName
    private var view: SearchContract.View? = null

    override fun attach(view: SearchContract.View) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    override fun doSearch(search: String) {
        Pandora(Pandora.Protocol.HTTP)
                .RequestBuilder(Search)
                .body(Search.RequestBody(search, true, true))
                .build<Search.ResponseBody>()
                .subscribe(this::handleSearchSuccess, this::handleSearchError)
    }

    private fun handleSearchSuccess(responseBody: Search.ResponseBody) {
        // TODO: Consider loading results into DB for offline use
        view?.showProgress(false)
        view?.updateSearchResults(responseBody.songs + responseBody.artists + responseBody.genreStations)
    }

    private fun handleSearchError(throwable: Throwable? = null) {
        throwable?.printStackTrace()
        view?.showProgress(false)
    }

    override fun createStation(musicToken: String) {

        Pandora(Pandora.Protocol.HTTP)
                .RequestBuilder(CreateStation)
                .body(CreateStation.RequestBody(musicToken))
                .build<ExpandedStationModel>()
                .subscribe(this::handleCreateStationSuccess, this::handleCreateStationError)
    }

    private fun handleCreateStationSuccess(response: ExpandedStationModel) {
        Log.i(TAG, response.toString())
        // TODO: Play station
        Player.play(response)
    }

    private fun handleCreateStationError(throwable: Throwable?) {
        throwable?.printStackTrace()
    }

}