package com.jeremiahzucker.pandroid.util

/**
 * TimeUtil
 *
 * Author: Jeremiah Zucker
 * Date:   9/6/2017
 * Desc:   TODO: Complete
 */
fun Int.formatDuration(): String {
    val temp = this / 1000 // milliseconds into seconds
    var minute = temp/ 60
    val hour = minute / 60
    minute %= 60
    val second = temp % 60
    return if (hour != 0)
        String.format("%2d:%02d:%02d", hour, minute, second)
    else
        String.format("%02d:%02d", minute, second)
}