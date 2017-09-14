package com.jeremiahzucker.pandroid.request.model

import com.google.gson.Gson
import com.google.gson.JsonElement

/**
 * Created by jzucker on 7/1/17.
 */
data class ResponseModel(val stat: String, val result: JsonElement, val message: String?, val code: Int) {
    val isOk get() = stat.equals("ok", true)
    inline fun <reified T> getResult(gson: Gson = Gson()): T = gson.fromJson(result, T::class.java)
}