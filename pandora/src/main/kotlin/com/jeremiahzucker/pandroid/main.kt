package com.jeremiahzucker.pandroid

import com.jeremiahzucker.pandroid.extensions.log
import com.jeremiahzucker.pandroid.models.ExpandedStationModel
import com.jeremiahzucker.pandroid.models.Response
import com.jeremiahzucker.pandroid.network.PandoraApi

inline fun <reified T> Response<T>.unwrap(): T = success.result

suspend fun main() = with<PandoraSdk, Unit>(PandoraSdk) {
    authenticate(
        "zucker.jeremiah+pandroid2@gmail.com",
        "pencil",
    )

    getStations().map(ExpandedStationModel::stationName).log()
}