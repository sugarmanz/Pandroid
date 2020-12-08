package com.jeremiahzucker.pandroid.ui.auth

import android.util.Log // TODO: Remove android class
import com.jeremiahzucker.pandroid.PandroidApplication.Companion.Preferences
import com.jeremiahzucker.pandroid.crypt.BlowFish
import com.jeremiahzucker.pandroid.request.Pandora
import com.jeremiahzucker.pandroid.request.json.v5.method.auth.PartnerLogin
import com.jeremiahzucker.pandroid.request.json.v5.method.auth.UserLogin
import com.jeremiahzucker.pandroid.util.hexStringToByteArray

/**
 * AuthPresenter
 *
 * Author: Jeremiah Zucker
 * Date:   8/29/2017
 * Desc:   TODO: Complete
 */
class AuthPresenter : AuthContract.Presenter {

    private val TAG: String = AuthPresenter::class.java.simpleName
    private var view: AuthContract.View? = null

    override fun attach(view: AuthContract.View) {
        this.view = view

        doPartnerLogin().subscribe(this::handlePartnerLoginSuccess)
    }

    override fun detach() {
        this.view = null
    }

    override fun attemptLogin(username: String?, password: String?) {
        if (view == null)
            return

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

            // TODO: Implement piggybacking in order preserve networking resources
            if (Preferences.partnerAuthToken == null)
                doPartnerLogin().subscribe(
                    {
                        handlePartnerLoginSuccess(it)
                        doUserLogin(username, password)
                    },
                    view!!::showErrorNetwork
                )
            else doUserLogin(username, password)
        }
    }

    private fun isPasswordValid(password: String) = password.length > 4
    private fun isUsernameValid(username: String) = username.any { it == '@' }

    // So much prettier <3 && TODO: Convert into store call (well, not for login call)
    private fun doUserLogin(username: String, password: String) {
        Preferences.username = username
        Preferences.password = password
        Pandora.HTTPS.RequestBuilder(UserLogin)
            .authToken(Preferences.partnerAuthToken)
            .body(UserLogin.RequestBody(username, password))
            .build<UserLogin.ResponseBody>()
            .subscribe(this::handleUserLoginSuccess, this::handleUserLoginError)
    }

    private fun handleUserLoginSuccess(result: UserLogin.ResponseBody) {
        // TODO: Pass to model? Maybe?
        Preferences.userId = result.userId
        Preferences.userAuthToken = result.userAuthToken

        view?.showMain()
    }

    private fun handleUserLoginError(throwable: Throwable) {
        // Oh no!
        Log.e(TAG, throwable.message, throwable)
        view?.showErrorNetwork(throwable)
    }

    private fun doPartnerLogin() = Pandora.HTTPS.RequestBuilder(PartnerLogin)
        .body(PartnerLogin.RequestBody())
        .encrypted(false)
        .build<PartnerLogin.ResponseBody>()

    private fun handlePartnerLoginSuccess(result: PartnerLogin.ResponseBody) {
        println(result.syncTime)
        println(decryptSyncTime(result.syncTime).toLong())
        println(decryptSyncTime(result.syncTime).toLong() - (System.currentTimeMillis() / 1000L))

        // Following Pithos impl. Differs from docs.
        Preferences.syncTimeOffset = decryptSyncTime(result.syncTime).toLong() - (System.currentTimeMillis() / 1000L)
        Preferences.partnerId = result.partnerId
        Preferences.partnerAuthToken = result.partnerAuthToken
    }

    private fun handlePartnerLoginError(throwable: Throwable) {
        view?.showErrorNetwork(throwable)
    }

    private fun decryptSyncTime(raw: String): String {
        val fugu = BlowFish()
        val decoded = raw.hexStringToByteArray()
        var decrypted = fugu.decrypt(decoded)

        decrypted = decrypted.copyOfRange(4, decrypted.size)

        return String(decrypted, Charsets.UTF_8)
    }
}
