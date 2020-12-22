package com.jeremiahzucker.pandroid.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
open class ExpandedStationModel(
    var stationId: String,
    var stationToken: String,
    var stationName: String,
    var suppressVideoAds: Boolean? = null,
    var isQuickMix: Boolean? = null,
    var stationDetailUrl: String? = null,
    var isShared: Boolean? = null,
    /** @Ignore */ var dateCreated: DateModel? = null,
    var stationSharingUrl: String? = null,
    var requiresCleanAds: Boolean? = null,
    var allowRename: Boolean? = null,
    var allowAddMusic: Boolean? = null,
    /** @Ignore */ var quickMixStationIds: List<String>? = null,
    var allowDelete: Boolean? = null,
    var allowEditDescription: Boolean? = null,
    var artUrl: String? = null,
    @Transient var dumb: String? = null
)/** : RealmObject() */ {

    constructor(stationId: String, stationToken: String, stationName: String) : this(
        stationId = stationId,
        stationToken = stationToken,
        stationName = stationName,
        dumb = "yes"
    )

    override fun toString() = "${this::class.simpleName}(name=${stationName})"
}
