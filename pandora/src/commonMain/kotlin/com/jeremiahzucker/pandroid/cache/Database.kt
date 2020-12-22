package com.jeremiahzucker.pandroid.cache

import com.jeremiahzucker.pandroid.models.ExpandedStationModel

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val databaseQuery = database.appDatabaseQueries

    internal fun getStations(): List<ExpandedStationModel> =
        databaseQuery.selectAllStations(::ExpandedStationModel).executeAsList()
    
    internal fun setStations(stations: List<ExpandedStationModel>) {
        databaseQuery.transaction {
            databaseQuery.removeAllStations()
            stations.forEach { station ->
                insertStation(station)
            }
        }
    }

    private fun insertStation(station: ExpandedStationModel) {
        databaseQuery.insertStation(
            stationId = station.stationId,
            stationToken = station.stationToken,
            stationName = station.stationName,
        )
    }

}