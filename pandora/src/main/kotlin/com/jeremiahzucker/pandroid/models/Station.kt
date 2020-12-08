package com.jeremiahzucker.pandroid.models

import kotlinx.serialization.Serializable

@Serializable
data class Station(
    val stationToken: String,
    val stationName: String,
    val stationId: String
)
