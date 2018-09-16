package com.jeremiahzucker.pandroid.request.json.v5.model

/**
 * Created by Jeremiah Zucker on 8/23/2017.
 */
data class TrackModel(
        val trackToken: String?,
        val artistName: String?,
        val albumName: String?,
        val amazonAlbumUrl: String?,
        val songExplorerUrl: String?,
        val albumArtUrl: String?,
        val artistDetailUrl: String?,
        val audioUrlMap: AudioUrlMap?,
        val itunesSongUrl: String?,
        val additionalAudioUrl: Any?, // List<String> OR String based on request
        val amazonAlbumAsin: String?,
        val amazonAlbumDigitalAsin: String?,
        val artistExplorerUrl: String?,
        val songName: String?,
        val albumDetailUrl: String?,
        val songDetailUrl: String?,
        val stationId: String?,
        var songRating: Int?,
        val trackGain: String?,
        val albumExplorerUrl: String?,
        val allowFeedback: Boolean?,
        val amazonSongDigitalAsin: String?,
        val nowPlayingStationAdUrl: String?,
        val adToken: String?,
        val trackLength: Int?,

        // Not returned, but manually combined
        var feedbackId: String?,
        var downloaded: Boolean = false,
        var path: String?
)