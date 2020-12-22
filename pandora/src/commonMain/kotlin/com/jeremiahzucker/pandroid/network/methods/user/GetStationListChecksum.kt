package com.jeremiahzucker.pandroid.network.methods.user

import com.jeremiahzucker.pandroid.network.methods.BaseMethod
import com.jeremiahzucker.pandroid.network.methods.UserRequestBody
import kotlinx.serialization.Serializable

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#user-getstationlist
 */
object GetStationListChecksum : BaseMethod() {

    @Serializable class RequestBody : UserRequestBody()

    @Serializable
    data class ResponseBody(
        val checksum: String
    )
}
