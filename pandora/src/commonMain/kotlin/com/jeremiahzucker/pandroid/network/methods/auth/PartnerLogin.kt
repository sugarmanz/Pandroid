package com.jeremiahzucker.pandroid.network.methods.auth

import ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray
import com.jeremiahzucker.pandroid.crypto.Cipher
import com.jeremiahzucker.pandroid.network.methods.BaseMethod
import kotlinx.serialization.Serializable

/**
 * Created by jzucker on 7/1/17.
 * https://6xq.net/pandora-apidoc/json/authentication/#partner-login
 */
object PartnerLogin : BaseMethod(false) {

    @Serializable
    data class RequestBody(
        val username: String = "android",
        val password: String = "AC7IBG09A3DTSYM4R41UJWL07VLN8JI7",
        val deviceModel: String = "android-generic",
        val version: String = "5",
        val includeUrls: Boolean = false,
        val returnDeviceType: Boolean = false,
        val returnUpdatePromptVersions: Boolean = false,
    )

    @Serializable
    data class ResponseBody(
        private val syncTime: String,
        val partnerId: String,
        val partnerAuthToken: String
    ) {
        val Cipher.processedSyncTimeOffset: Long get() = syncTime
            .decryptSyncTime(this)
            .toLong()
            .offsetCurrentTime()

        fun decryptSyncTimeOffset(cipher: Cipher): Long = cipher.processedSyncTimeOffset

        private fun Long.offsetCurrentTime() = this - (System.currentTimeMillis() / 1000L)

        private fun String.decryptSyncTime(cipher: Cipher) = hexStringToByteArray(this)
            .let(cipher::decrypt)
            .run { copyOfRange(4, size) }
            .let(::String)
    }
}
