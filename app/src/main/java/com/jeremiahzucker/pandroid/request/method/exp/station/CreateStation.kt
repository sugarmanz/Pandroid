package com.jeremiahzucker.pandroid.request.method.exp.station

import com.jeremiahzucker.pandroid.request.method.Method
import com.jeremiahzucker.pandroid.request.model.DateModel
import com.jeremiahzucker.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#create
 */
object CreateStation: Method() {

    enum class MusicType {
        SONG,
        ARTIST
    }

    data class RequestBody private constructor(
            val musicToken: String?,
            val trackToken: String? = null,
            val musicType: String? = null
    ) : SyncTokenRequestBody(TokenType.USER) {

        constructor(musicToken: String) : this(
                musicToken = musicToken,
                trackToken = null,
                musicType = null
        )

        constructor(trackToken: String, musicType: MusicType) : this(
                musicToken = null,
                trackToken = trackToken,
                musicType = musicType.name.toLowerCase()
        )
    }

    data class ResponseBody(
            val suppressVideoAds: Boolean,
            val stationId: String,
            val allowAddMusic: Boolean,
            val dateCreated: DateModel,
            val stationDetailUrl: String,
            val allowEditDescription: Boolean,
            val requiresCleanAds: Boolean,
            val isGenreStation: Boolean,
            val stationToken: String,
            val stationName: String,
            val isShared: Boolean,
            val allowDelete: Boolean,
            val genre: List<String>, // TODO: Verify type
            val isQuickMix: Boolean,
            val allowRename: Boolean,
            val stationSharingUrl: String
    )
}