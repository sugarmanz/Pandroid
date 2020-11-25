package com.jeremiahzucker.pandroid.request.json.v5.model

/**
 * Created by Jeremiah Zucker on 8/23/2017.
 */
data class AudioUrlMap(
    val highQuality: AudioUrl,
    val mediumQuality: AudioUrl,
    val lowQuality: AudioUrl
)
