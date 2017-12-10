package com.jeremiahzucker.pandroid.request.json.v5.model

/**
 * Created by Jeremiah Zucker on 8/23/2017.
 */
data class DateModel(
        val date: Int,
        val day: Int,
        val hours: Int,
        val minutes: Int,
        val month: Int,
        val seconds: Int,
        val time: Long,
        val timezoneOffset: Int,
        val year: Int
)