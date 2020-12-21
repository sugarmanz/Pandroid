package com.jeremiahzucker.pandroid.models

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val stat: String,
    val message: String,
    val code: String,
)