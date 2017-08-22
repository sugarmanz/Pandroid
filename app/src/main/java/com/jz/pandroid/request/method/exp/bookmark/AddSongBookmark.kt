package com.jz.pandroid.request.method.exp.bookmark

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/bookmarks/#add-song-bookmark
 */
object AddSongBookmark: Method() {
    data class RequestBody(
            val trackToken: String
    ) : SyncTokenRequestBody(TokenType.USER)
}