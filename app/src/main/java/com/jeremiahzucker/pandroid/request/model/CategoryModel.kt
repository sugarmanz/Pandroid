package com.jeremiahzucker.pandroid.request.model

/**
 * Created by Jeremiah Zucker on 8/23/2017.
 */
data class CategoryModel(
        val categoryName: String,
        val stations: List<StationModel>
)