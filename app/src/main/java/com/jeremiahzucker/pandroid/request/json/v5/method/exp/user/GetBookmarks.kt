package com.jeremiahzucker.pandroid.request.json.v5.method.exp.user

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.ArtistBookmarkModel
import com.jeremiahzucker.pandroid.request.json.v5.model.SongBookmarkModel
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/bookmarks/#user-getbookmarks
 */
object GetBookmarks : BaseMethod() {
    fun RequestBody() = SyncTokenRequestBody(SyncTokenRequestBody.TokenType.USER)

    data class ResponseBody(
        val artists: List<ArtistBookmarkModel>,
        val songs: List<SongBookmarkModel>
    )
}
