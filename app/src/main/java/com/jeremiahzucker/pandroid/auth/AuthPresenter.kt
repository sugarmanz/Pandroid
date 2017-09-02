package com.jeremiahzucker.pandroid.auth

import android.util.Log // TODO: Remove android class
import com.jeremiahzucker.pandroid.Preferences
import com.jeremiahzucker.pandroid.request.BasicCallback
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

    fun handleSuccess(result: UserLogin.ResponseBody) {
        Log.i(TAG, "Handling success")
        // TODO: Pass to model? Maybe?
        Preferences.userId = result.userId
        Preferences.userAuthToken = result.userAuthToken

        view?.showMain()
    }

    fun handleCommonError(throwable: Throwable? = null) {
        // Oh no!
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
//            userLoginCall = Pandora().RequestBuilder(UserLogin.methodName)
//                    .authToken(Preferences.partnerAuthToken)
//                    .body(UserLogin.RequestBody(username, password))
//                    .build()

            val observable  = Pandora().RequestBuilder(UserLogin.methodName)
                    .authToken(Preferences.partnerAuthToken)
                    .body(UserLogin.RequestBody(username, password))
                    .buildObservable()

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter { it.isOk }
                    .map { it.getResult<UserLogin.ResponseBody>() }
                    .subscribe(this::handleSuccess, this::handleCommonError)

            Log.i(TAG, "Making Call")
//            userLoginCall?.enqueue(object : BasicCallback<ResponseModel>() {
//                override fun handleSuccess(responseModel: ResponseModel) {
//                    val result = responseModel.getResult<UserLogin.ResponseBody>()
//                    if (responseModel.isOk && result != null) {
//                        Log.i(TAG, "Handling success")
//                        // TODO: Pass to model? Maybe?
//                        Preferences.userId = result.userId
//                        Preferences.userAuthToken = result.userAuthToken
//
//                        view?.showMain()
//                    } else {
//                        handleCommonError()
//                    }
//                }
//
//                override fun handleConnectionError() {
//                    Log.e(TAG, "Connection Error")
//                }
//
//                override fun handleStatusError(responseCode: Int) {
//                    Log.e(TAG, "Status Error: " + responseCode.toString())
//                }
//
//                override fun handleCommonError() {
//                    Log.e(TAG, "Common Error")
//                    view?.showErrorPasswordIncorrect()
//                }
//
//                override fun onFinish() {
//                    Log.i(TAG, "Call finished")
//                    userLoginCall = null
//                    view?.showProgress(false)
//                }
//
//            })
        }
    }

    private fun isPasswordValid(password: String) = password.length > 4
    private fun isUsernameValid(username: String) = username.any { it == '@' }
}