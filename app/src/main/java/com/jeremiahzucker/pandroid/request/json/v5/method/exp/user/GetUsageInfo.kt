package com.jeremiahzucker.pandroid.request.json.v5.method.exp.user

import com.jeremiahzucker.pandroid.request.BaseMethod
import com.jeremiahzucker.pandroid.request.json.v5.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/account/#user-getusageinfo
 */
object GetUsageInfo : BaseMethod() {
    fun RequestBody() = SyncTokenRequestBody(SyncTokenRequestBody.TokenType.USER)

    data class ResponseBody(
        val accountMonthlyListening: Int,
        val deviceMonthlyListening: Int,
        val monthlyCapHours: Int,
        val monthlyCapWarningPercent: Int,
        val monthlyCapWarningRepeatPercent: Int,
        val isMonthlyPayer: Boolean,
        val isCapped: Boolean,
        val listeningTimestamp: Int
    )
}
