package com.jz.pandroid.request.method.exp.user

import com.jz.pandroid.request.method.Method

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/account/#user-startcomplimentarytrial
 */
object StartComplimentaryTrial: Method() {
    data class RequestBody(
            val complimentarySponsor: String
    )
}