package com.jeremiahzucker.pandroid.models

import kotlinx.serialization.Serializable

/**
 * Created by Jeremiah Zucker on 8/23/2017.
 */
@Serializable
data class AudioUrlMap(
    val highQuality: AudioUrl,
    val mediumQuality: AudioUrl,
    val lowQuality: AudioUrl
)
