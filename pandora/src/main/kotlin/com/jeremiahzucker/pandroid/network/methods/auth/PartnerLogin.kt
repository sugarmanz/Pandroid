package com.jeremiahzucker.pandroid.network.methods.auth

import ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray
import com.jeremiahzucker.pandroid.network.crypt.BlowFish
import com.jeremiahzucker.pandroid.network.methods.BaseMethod
import kotlinx.serialization.Serializable

/**
 * Created by jzucker on 7/1/17.
 * https://6xq.net/pandora-apidoc/json/authentication/#partner-login
 */
object PartnerLogin : BaseMethod() {

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
        val syncTime: String,
        val partnerId: String,
        val partnerAuthToken: String
    ) {
        val processedSyncTimeOffset: Long get() = syncTime
            .decrypt()
            .toLong()
            .offset()

        private fun Long.offset() = this - (System.currentTimeMillis() / 1000L)

        private fun String.decrypt(): String {
            val fugu = BlowFish()
            val decoded = hexStringToByteArray()
            var decrypted = fugu.decrypt(decoded)

            decrypted = decrypted.copyOfRange(4, decrypted.size)

            return String(decrypted, Charsets.UTF_8).also(::println)
        }

        private fun String.hexStringToByteArray(): ByteArray {
            val len = length
            val data = ByteArray(len / 2)
            var i = 0
            while (i < len) {
                data[i / 2] = ((Character.digit(this[i], 16) shl 4) + Character.digit(this[i + 1], 16)).toByte()
                i += 2
            }
            return data
        }
    }
}
