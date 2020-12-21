package com.jeremiahzucker.pandroid.models

import kotlinx.serialization.Serializable

/**
 * Created by Jeremiah Zucker on 8/23/2017.
 */
@Serializable
data class AudioUrl(
    val bitrate: String,
    val encoding: String,
    val audioUrl: String,
    val protocol: String
)
