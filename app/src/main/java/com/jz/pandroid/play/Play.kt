package com.jz.pandroid.play

import com.jz.pandroid.request.BasicCallback
import com.jz.pandroid.request.Pandora
import com.jz.pandroid.request.method.exp.station.GetPlaylist
import com.jz.pandroid.request.method.exp.user.GetStationList
import com.jz.pandroid.request.model.ResponseModel
import retrofit2.Call

/**
 * Created by Jeremiah Zucker on 8/25/2017.
 */
class Play {

    private val TAG = Play::class.java.simpleName

    private var getStationListCall: Call<ResponseModel>? = null
    private var getPlaylistCall: Call<ResponseModel>? = null

    fun getStationList(body: GetStationList.RequestBody, success: (GetStationList.ResponseBody) -> Unit) {
        if (getStationListCall == null) {
            getStationListCall = Pandora(Pandora.Protocol.HTTP).RequestBuilder(GetStationList).body(body).build()
            getStationListCall?.enqueue(object : BasicCallback<ResponseModel>() {
                override fun handleSuccess(responseModel: ResponseModel) {
                    val result = responseModel.getResult<GetStationList.ResponseBody>()
                    if (responseModel.isOk && result != null) {
                        success(result)
                    } else {
                        handleCommonError()
                    }
                }

                override fun handleConnectionError() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun handleStatusError(responseCode: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun handleCommonError() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onFinish() {
                    getStationListCall = null
                }
            })
        }
    }

    fun getPlaylist(body: GetPlaylist.RequestBody, success: (GetPlaylist.ResponseBody) -> Unit) {
        if (getPlaylistCall == null) {
            getPlaylistCall = Pandora().RequestBuilder(GetPlaylist).body(body).build()
            getPlaylistCall?.enqueue(object : BasicCallback<ResponseModel>() {
                override fun handleSuccess(responseModel: ResponseModel) {
                    val result = responseModel.getResult<GetPlaylist.ResponseBody>()
                    if (responseModel.isOk && result != null) {
                        success(result)
                    } else {
                        handleCommonError()
                    }
                }

                override fun handleConnectionError() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun handleStatusError(responseCode: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun handleCommonError() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onFinish() {
                    getPlaylistCall = null
                }
            })
        }
    }

}