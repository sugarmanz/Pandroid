package com.jeremiahzucker.pandroid.request.json.v5.model

/**
 * Created by Jeremiah Zucker on 8/23/2017.
 */
data class SongSeedModel(
        val seedId: String,
        val musicToken: String,
        val songName: String,
        val artistName: String,
        val artUrl: String
)