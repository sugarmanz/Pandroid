package com.jeremiahzucker.pandroid.auth

import com.jeremiahzucker.pandroid.BasePresenter
import com.jeremiahzucker.pandroid.BaseView

/**
 * Created by Jeremiah Zucker on 8/28/2017.
 */
interface AuthContract {

    interface View : BaseView<Presenter> {

        fun showProgress(show: Boolean)
        fun showErrorUsernameRequired()
        fun showErrorUsernameInvalid()
        fun showErrorPasswordRequired()
        fun showErrorPasswordInvalid()
        fun showErrorPasswordIncorrect()
        fun clearErrors()
        fun showMain() // TODO: Rename

    }

    interface Presenter : BasePresenter<View> {

        fun checkAuth()
        fun attemptLogin(username: String?, password: String?)

    }

}