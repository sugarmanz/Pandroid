package com.jeremiahzucker.pandroid.request.json.v5.model

/**
 * Created by Jeremiah Zucker on 8/25/2017.
 * TODO: Try to consolidate duplicate fields
 */
data class SongBookmarkModel(
        val sampleUrl: String,
        val sampleGain: String,
        val albumName: String,
        val artistName: String,
        val musicToken: String,
        val dateCreated: DateModel,
        val artUrl: String,
        val bookmarkToken: String,
        val songName: String
)