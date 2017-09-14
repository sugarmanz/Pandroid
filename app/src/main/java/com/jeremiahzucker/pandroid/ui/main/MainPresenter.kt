package com.jeremiahzucker.pandroid.ui.main

/**
 * MainPresenter
 *
 * Author: Jeremiah Zucker
 * Date:   9/4/2017
 * Desc:   TODO: Complete
 */
class MainPresenter : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    override fun onStationClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}