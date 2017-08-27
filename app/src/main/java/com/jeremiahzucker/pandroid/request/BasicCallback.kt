package com.jeremiahzucker.pandroid.request

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * Created by jzucker on 7/1/17.
 * This abstract class exists to create a set of common hooks for the Callback class
 */

//import com.jimmified.search.JimmifyApplication

abstract class BasicCallback<T> : Callback<T> {

    private val genericName: String
        get() = ((javaClass
                .genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>).simpleName

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.code() != 200) {
//            JimmifyApplication.showToast(genericName + " (" + response.code() + ")")
            handleStatusError(response.code())
        } else {
            handleSuccess(response.body())
        }
        onFinish()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
        when(t) {
            is UnknownHostException,
            is ConnectException -> handleConnectionError()
        }
        onFinish()
    }

    /**
     * Dev Reference
     *  fun handleSuccess(responseModel: T) {
     *      if (responseModel.isSuccess()) {
     *          // DO STUFF HERE
     *      } else {
     *          handleCommonError()
     *      }
     *  }

     * @param responseModel
     */
    abstract fun handleSuccess(responseModel: T)

    /**
     * Dev Reference
     *  fun handleConnectionError() {
     *      // Display toast or something
     *  }
     */
    abstract fun handleConnectionError()

    /**
     * Dev Reference
     *  fun handleStatusError(responseCode: Int) {
     *      when(responseCode) {
     *          500 -> handleConnectionError()      // INTERNAL SERVER ERROR
     *          400 -> {}                           // REQUEST ERROR
     *          401, else -> handleCommonError()    // USER ERROR
     *      }
     *  }
     * @param responseCode response status code
     */
    abstract fun handleStatusError(responseCode: Int)

    /**
     * Dev Reference
     *  fun handleCommonError() {
     *      // Do user error handling here
     *  }
     */
    abstract fun handleCommonError()

    /**
     * Dev Reference
     *  fun onFinish() {
     *      // Other onFinish functionality
     *      call = null
     *  }
     */
    abstract fun onFinish()
}