package com.jeremiahzucker.pandroid.request.json.v5.method.exp.station

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#create
 */
object CreateStation : BaseMethod() {

    enum class MusicType {
        SONG,
        ARTIST
    }

    data class RequestBody private constructor(
        val trackToken: String?,
        val musicToken: String?,
        val musicType: String?
    ) : SyncTokenRequestBody(TokenType.USER) {

        constructor(trackToken: String) : this(
            trackToken = trackToken,
            musicToken = null,
            musicType = null
        )

        constructor(musicToken: String, musicType: MusicType) : this(
            trackToken = null,
            musicToken = musicToken,
            musicType = musicType.name.toLowerCase()
        )
    }
}
