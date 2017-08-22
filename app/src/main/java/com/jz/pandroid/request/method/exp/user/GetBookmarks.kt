package com.jz.pandroid.request.method.exp.user

import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/bookmarks/#user-getbookmarks
 */
object GetBookmarks {
    fun RequestBody() = SyncTokenRequestBody(SyncTokenRequestBody.TokenType.USER)

    val methodName: String = "user.getBookmarks"
}