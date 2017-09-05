package com.jeremiahzucker.pandroid.ui.auth

import com.jeremiahzucker.pandroid.ui.base.BasePresenter
import com.jeremiahzucker.pandroid.ui.base.BaseView

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