package com.jz.pandroid.request.crypt

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.jz.pandroid.request.model.EncryptedRequest
import java.lang.reflect.Type

/**
 * Created by jzucker on 7/2/17.
 */
class EncryptionSerializer: JsonSerializer<EncryptedRequest> {

    override fun serialize(src: EncryptedRequest, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        var json = context?.serialize(src, typeOfSrc)

        if (json == null)
            json = JsonObject()

        return json
    }

}