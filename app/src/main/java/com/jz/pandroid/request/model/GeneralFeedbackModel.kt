package com.jz.pandroid.request.model

/**
 * Created by Jeremiah Zucker on 8/23/2017.
 */
data class GeneralFeedbackModel(
        val thumbsUp: List<FeedbackModel>,
        val totalThumbsUp: Int,
        val thumbsDown: List<FeedbackModel>,
        val totalThumbsDown: Int
)