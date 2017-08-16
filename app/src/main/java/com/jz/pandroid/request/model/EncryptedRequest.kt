package com.jz.pandroid.request.model

import com.jz.pandroid.request.MyRequestBody

/**
 * Created by jzucker on 7/2/17.
 */
open class EncryptedRequest : MyRequestBody() {
    val SHOULDNT_BE_SERIALIZED = "Y THO?"
}