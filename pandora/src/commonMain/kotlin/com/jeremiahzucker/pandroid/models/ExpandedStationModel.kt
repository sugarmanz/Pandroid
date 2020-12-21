package com.jeremiahzucker.pandroid.models

import kotlinx.serialization.Serializable

@Serializable
open class ExpandedStationModel(
    var suppressVideoAds: Boolean? = null,
    var isQuickMix: Boolean? = null,
    var stationId: String? = null,
    var stationDetailUrl: String? = null,
    var isShared: Boolean? = null,
    /** @Ignore */ var dateCreated: DateModel? = null,
    /** @PrimaryKey */ var stationToken: String? = null,
    var stationName: String? = null,
    var stationSharingUrl: String? = null,
    var requiresCleanAds: Boolean? = null,
    var allowRename: Boolean? = null,
    var allowAddMusic: Boolean? = null,
    /** @Ignore */ var quickMixStationIds: List<String>? = null,
    var allowDelete: Boolean? = null,
    var allowEditDescription: Boolean? = null,
    var artUrl: String? = null
)/** : RealmObject() */
