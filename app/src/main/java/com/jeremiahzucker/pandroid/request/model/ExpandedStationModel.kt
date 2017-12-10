package com.jeremiahzucker.pandroid.request.model

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

/**
 * Created by Jeremiah Zucker on 8/25/2017.
 */
open class ExpandedStationModel(
        var suppressVideoAds: Boolean? = null,
        var isQuickMix: Boolean? = null,
        var stationId: String? = null,
        var stationDetailUrl: String? = null,
        var isShared: Boolean? = null,
        @Ignore var dateCreated: DateModel? = null,
        @PrimaryKey var stationToken: String? = null,
        var stationName: String? = null,
        var stationSharingUrl: String? = null,
        var requiresCleanAds: Boolean? = null,
        var allowRename: Boolean? = null,
        var allowAddMusic: Boolean? = null,
        @Ignore var quickMixStationIds: List<String>? = null,
        var allowDelete: Boolean? = null,
        var allowEditDescription: Boolean? = null,
        var artUrl: String? = null
) : RealmObject()