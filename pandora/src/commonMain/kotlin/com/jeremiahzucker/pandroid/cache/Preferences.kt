package com.jeremiahzucker.pandroid.cache

expect object Preferences {

    var username: String?
    var password: String?
    var stationListChecksum: String?

    var partnerId: String?
    var userId: String?
    var userAuthToken: String?
    var partnerAuthToken: String?
    var syncTimeOffset: Long?

}
