package com.jeremiahzucker.pandroid.util

import android.graphics.drawable.GradientDrawable
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.view.Window

/**
 * WindowUtil
 *
 * Author: Jeremiah Zucker
 * Date:   9/4/2017
 * Desc:   TODO: Complete
 */

fun Window.setBackgroundGradient(
        @ColorInt startColor: Int,
        @ColorInt endColor: Int, radius: Int,
        @FloatRange(from = 0.0, to = 1.0) centerX: Float,
        @FloatRange(from = 0.0, to = 1.0) centerY: Float
) {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.colors = intArrayOf(startColor, endColor)
    gradientDrawable.gradientType = GradientDrawable.RADIAL_GRADIENT
    gradientDrawable.gradientRadius = radius.toFloat()
    gradientDrawable.setGradientCenter(centerX, centerY)
    this.setBackgroundDrawable(gradientDrawable)
}