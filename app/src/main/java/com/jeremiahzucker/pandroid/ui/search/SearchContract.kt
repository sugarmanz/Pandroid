package com.jeremiahzucker.pandroid.ui.search

import com.jeremiahzucker.pandroid.request.method.exp.music.Search
import com.jeremiahzucker.pandroid.ui.base.BasePresenter
import com.jeremiahzucker.pandroid.ui.base.BaseView
import com.jeremiahzucker.pandroid.request.method.exp.user.GetStationList
import com.jeremiahzucker.pandroid.request.model.ExpandedStationModel

/**
 * SearchContract
 *
 * Author: Jeremiah Zucker
 * Date:   9/2/2017
 * Desc:   TODO: Complete
 */
interface SearchContract {

    interface View : BaseView<Presenter> {

        fun showProgress(show: Boolean)

        // TODO: Handle different kind of search results (artist, song, station)
        fun updateSearchResults(results: List<Search.SearchResult>)

    }

    interface Presenter : BasePresenter<View> {

        fun doSearch(search: String)
        fun createStation(musicToken: String)

    }

}