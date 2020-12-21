package com.jeremiahzucker.pandroid.models

import kotlinx.serialization.Serializable

@Serializable
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
