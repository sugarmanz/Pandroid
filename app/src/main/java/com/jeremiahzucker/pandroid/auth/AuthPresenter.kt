package com.jeremiahzucker.pandroid.auth

import android.util.Log // TODO: Remove android class
import com.jeremiahzucker.pandroid.Preferences
import com.jeremiahzucker.pandroid.request.Pandora
import com.jeremiahzucker.pandroid.request.method.auth.UserLogin
import com.jeremiahzucker.pandroid.request.model.ResponseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call


/**
 * AuthPresenter
 *
 * Author: Jeremiah Zucker
 * Date:   8/29/2017
 * Desc:   TODO: Complete
 */
class AuthPresenter : AuthContract.Presenter {

    val TAG: String = AuthPresenter::class.java.simpleName
    var view: AuthContract.View? = null
    var userLoginCall: Call<ResponseModel>? = null

    override fun attach(view: AuthContract.View) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    override fun checkAuth() {
        if (view == null)
            return

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

            // So much prettier <3 && TODO: Convert into store call
            val observable  = Pandora().RequestBuilder(UserLogin.methodName)
                    .authToken(Preferences.partnerAuthToken)
                    .body(UserLogin.RequestBody(username, password))
                    .buildObservable()

            Log.i(TAG, "Making Call")
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter { it.isOk }
                    .map { it.getResult<UserLogin.ResponseBody>() }
                    .subscribe(this::handleUserLoginSuccess, this::handleUserLoginError)
        }
    }

    private fun handleUserLoginSuccess(result: UserLogin.ResponseBody) {
        Log.i(TAG, "Handling success")
        Log.i(TAG, result.toString())
        // TODO: Pass to model? Maybe?
        Preferences.userId = result.userId
        Preferences.userAuthToken = result.userAuthToken

        view?.showMain()
    }

    private fun handleUserLoginError(throwable: Throwable? = null) {
        // Oh no!
        Log.e(TAG, throwable?.message ?: "Error!", throwable)
    }

    private fun isPasswordValid(password: String) = password.length > 4
    private fun isUsernameValid(username: String) = username.any { it == '@' }
}