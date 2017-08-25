package com.jz.pandroid.request.audio

/**
 * Created by Jeremiah Zucker on 8/23/2017.
 */
enum class AudioUrlFormat {
    HTTP_40_AAC_MONO,
    HTTP_64_AAC,
    HTTP_32_AACPLUS,
    HTTP_64_AACPLUS,
    HTTP_24_AACPLUS_ADTS,
    HTTP_32_AACPLUS_ADTS,
    HTTP_64_AACPLUS_ADTS,
    HTTP_128_MP3,
    HTTP_32_WMA;

    companion object {
        fun buildAudioUrlString(audioUrls: List<AudioUrlFormat>?): String? {
            return audioUrls?.joinToString(",") { it.name }
        }
    }
}