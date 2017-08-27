package com.jeremiahzucker.pandroid.request.method.exp.track

import com.jeremiahzucker.pandroid.request.method.Method
import com.jeremiahzucker.pandroid.request.model.ExplanationModel
import com.jeremiahzucker.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/play/#explain-track-choice
 */
object ExplainTrack: Method() {
    data class RequestBody(
            val trackToken: String
    ) : SyncTokenRequestBody(TokenType.USER)

    data class ResponseBody(
            val explanations: List<ExplanationModel>
    )
}