package com.jeremiahzucker.pandroid.request.json.v5.method.exp.track

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.ExplanationModel
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/play/#explain-track-choice
 */
object ExplainTrack : BaseMethod() {
    data class RequestBody(
        val trackToken: String
    ) : SyncTokenRequestBody(TokenType.USER)

    data class ResponseBody(
        val explanations: List<ExplanationModel>
    )
}
