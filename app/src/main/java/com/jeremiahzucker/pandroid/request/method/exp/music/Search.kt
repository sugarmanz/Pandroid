package com.jeremiahzucker.pandroid.request.method.exp.music

import com.jeremiahzucker.pandroid.request.method.Method
import com.jeremiahzucker.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/stations/#music-search
 */
object Search: Method() {
    data class RequestBody(
            val searchText: String,
            val includeNearMatches: Boolean? = null,
            val includeGenreStations: Boolean? = null
    ) : SyncTokenRequestBody(TokenType.USER)

    data class ResponseBody(
            val nearMatchesAvailable: Boolean,
            val explanation: String,
            val songs: List<SearchResult>,
            val artists: List<SearchResult>,
            val genreStations: List<SearchResult>
    )

    data class SearchResult(
            val musicToken: String,
            val score: Int,
            val songName: String?,
            val artistName: String?,
            val stationName: String?,
            val likelyMatch: Boolean?
    ) {
        val name: String get() {
            return when (musicToken[0]) {
                'S' -> songName + ", " + artistName
                'A', 'C' -> artistName
                'G' -> stationName
                else -> null
            } ?: musicToken
        }
    }
}