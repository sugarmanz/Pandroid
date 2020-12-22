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
        fun showErrorNetwork(throwable: Throwable)
        fun clearErrors()
        fun showMain()
    }

    interface Presenter : BasePresenter<View> {
        suspend fun attemptLogin(username: String?, password: String?)
    }
}
