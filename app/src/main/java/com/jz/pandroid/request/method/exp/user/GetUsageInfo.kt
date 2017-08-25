package com.jz.pandroid.request.method.exp.user

import com.jz.pandroid.request.method.Method
import com.jz.pandroid.request.model.SyncTokenRequestBody

/**
 * Created by Jeremiah Zucker on 8/22/2017.
 * https://6xq.net/pandora-apidoc/json/account/#user-getusageinfo
 */
object GetUsageInfo: Method() {
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