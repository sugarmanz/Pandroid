package com.jeremiahzucker.pandroid.request.json.v5.model

/**
 * Created by Jeremiah Zucker on 8/23/2017.
 */
data class StationSeedsModel(
        val songs: List<SongSeedModel>,
        val artists: List<ArtistSeedModel>,
        val genres: List<GenreSeedModel>
)