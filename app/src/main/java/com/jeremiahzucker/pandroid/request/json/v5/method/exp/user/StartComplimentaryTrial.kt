package com.jeremiahzucker.pandroid.request.json.v5.method.exp.user

import com.jeremiahzucker.pandroid.request.BaseMethod

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/account/#user-startcomplimentarytrial
 */
object StartComplimentaryTrial: BaseMethod() {
    data class RequestBody(
            val complimentarySponsor: String
    )
}