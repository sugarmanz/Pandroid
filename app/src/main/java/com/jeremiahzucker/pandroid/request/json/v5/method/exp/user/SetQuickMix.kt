package com.jeremiahzucker.pandroid.request.json.v5.method.exp.user

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#modify-quickmix
 */
object SetQuickMix: BaseMethod() {

    data class RequestBody(
            val quickMixStationIds: List<String>
    ) : SyncTokenRequestBody(TokenType.USER)

}