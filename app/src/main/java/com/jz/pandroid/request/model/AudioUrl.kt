package com.jz.pandroid.request.model

/**
 * Created by Jeremiah Zucker on 8/23/2017.
 */
data class AudioUrl(
        val bitrate: String,
        val encoding: String,
        val audioUrl: String,
        val protocol: String
)