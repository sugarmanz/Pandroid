package com.jeremiahzucker.pandroid.ui.auth

import android.util.Log
import com.jeremiahzucker.pandroid.PandoraSdk
import com.jeremiahzucker.pandroid.cache.Preferences

class AuthPresenter : AuthContract.Presenter {

    private val TAG: String = AuthPresenter::class.java.simpleName
    private var view: AuthContract.View? = null
    private val sdk = PandoraSdk()

    override fun attach(view: AuthContract.View) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    override suspend fun attemptLogin(username: String?, password: String?) {
        require(view != null)

        // Reset errors.
        view?.clearErrors()

        if (username == null) {
            view?.showErrorUsernameRequired()
        } else if (password == null) {
            view?.showErrorPasswordRequired()
        } else if (!isUsernameValid(username)) {
            view?.showErrorUsernameInvalid()
        } else if (!isPasswordValid(password)) {
            view?.showErrorPasswordInvalid()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            view?.showProgress(true)

            authenticate(username, password)
        }
    }

    private fun isPasswordValid(password: String) = password.length > 4
    private fun isUsernameValid(username: String) = username.any { it == '@' }

    private suspend fun authenticate(username: String, password: String) {
        try {
            sdk.authenticate(username, password)
            view?.showMain()
        } catch (exception: Exception) {
            Log.e(TAG, exception.message, exception)
            view?.showProgress(false)
            view?.showErrorNetwork(exception)
        }
    }

}
