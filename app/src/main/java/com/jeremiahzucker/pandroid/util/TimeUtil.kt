package com.jeremiahzucker.pandroid.util

/**
 * TimeUtil
 *
 * Author: Jeremiah Zucker
 * Date:   9/6/2017
 * Desc:   TODO: Complete
 */
fun Int.formatDurationFromMilliseconds() = (this / 1000).formatDurationFromSeconds()

fun Int.formatDurationFromSeconds(): String {
    var minute = this / 60
    val hour = minute / 60
    minute %= 60
    val second = this % 60
    return if (hour != 0)
        String.format("%2d:%02d:%02d", hour, minute, second)
    else
        String.format("%02d:%02d", minute, second)
}
