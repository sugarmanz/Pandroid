package com.jeremiahzucker.pandroid.models

import kotlinx.serialization.Serializable

/**
 * Created by Jeremiah Zucker on 8/23/2017.
 */
@Serializable
data class TrackModel(
    val trackToken: String? = null,
    val artistName: String? = null,
    val albumName: String? = null,
    // val amazonAlbumUrl: String?,
    val songExplorerUrl: String? = null,
    val albumArtUrl: String? = null,
    val artistDetailUrl: String? = null,
    val audioUrlMap: AudioUrlMap? = null,
    // val itunesSongUrl: String?,
    val additionalAudioUrl: StringUnionType = StringUnionType.Collection(),
    // val amazonAlbumAsin: String?,
    // val amazonAlbumDigitalAsin: String?,
    val artistExplorerUrl: String? = null,
    val songName: String? = null,
    val albumDetailUrl: String? = null,
    val songDetailUrl: String? = null,
    val stationId: String? = null,
    var songRating: Int? = null,
    val trackGain: String? = null,
    val albumExplorerUrl: String? = null,
    val allowFeedback: Boolean? = null,
    // val amazonSongDigitalAsin: String?,
    // val nowPlayingStationAdUrl: String?,
    // val adToken: String?,
    val trackLength: Int? = null,

    // Not returned, but manually combined
    var feedbackId: String? = null,
    var downloaded: Boolean = false,
    var path: String? = null,
)

