package com.jeremiahzucker.pandroid

/**
 * Created by Jeremiah Zucker on 8/28/2017.
 */
interface BasePresenter<T> {

    /**
     * Binds presenter with View.
     */
    fun attach(view: T)

    /**
     * Unbinds present when View is destroyed.
     */
    fun detach()

}