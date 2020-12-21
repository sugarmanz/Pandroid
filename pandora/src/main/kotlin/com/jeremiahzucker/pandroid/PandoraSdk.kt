package com.jeremiahzucker.pandroid

import com.jeremiahzucker.pandroid.cache.Preferences
import com.jeremiahzucker.pandroid.models.ExpandedStationModel
import com.jeremiahzucker.pandroid.network.PandoraApi
import com.jeremiahzucker.pandroid.network.methods.auth.PartnerLogin
import com.jeremiahzucker.pandroid.network.methods.auth.UserLogin

/** expect */ val partnerUsername: String = "android"
/** expect */ val partnerPassword: String = "AC7IBG09A3DTSYM4R41UJWL07VLN8JI7"
/** expect */ val deviceModel: String = "android-generic"

object PandoraSdk/**(databaseDriverFactory: DatabaseDriverFactory)*/ {

    // private val database = Database(databaseDriverFactory)
    private val api = PandoraApi()

    @Throws(Exception::class) suspend fun authenticate(username: String, password: String) {
        api.partnerLogin(PartnerLogin.RequestBody(
            username = partnerUsername,
            password = partnerPassword,
            deviceModel = deviceModel,
        )).let { response ->
            response.success.result.apply {
                Preferences.partnerId = partnerId
                Preferences.partnerAuthToken = partnerAuthToken
                Preferences.syncTimeOffset = api.cipher.processedSyncTimeOffset
            }
        }
        api.userLogin(UserLogin.RequestBody(username, password)).let { response ->
            response.success.result.apply {
                Preferences.userAuthToken = userAuthToken
                Preferences.userId = userId
            }
        }
    }

    @Throws(Exception::class) suspend fun getStations(forceReload: Boolean = false): List<ExpandedStationModel> {
        // TODO: Do checksum & check validation?
        val cachedStations = emptyList<ExpandedStationModel>() //database.getStations()
        return if (cachedStations.isNotEmpty() && !forceReload) {
            cachedStations
        } else {
            api.getStations().also {
                // database.clearDatabase()
                // database.createLaunches(it)
            }.success.result.stations
        }
    }

}