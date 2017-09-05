package com.jeremiahzucker.pandroid.player

/**
 * PlayMode
 *
 * Author: Jeremiah Zucker
 * Date:   9/4/2017
 * Desc:   TODO: Complete
 */
enum class PlayMode {
    SINGLE, LOOP, LIST, SHUFFLE;

    companion object {
        fun getDefault() = LOOP
        fun switchNextMode(current: PlayMode?): PlayMode {
            if (current == null)
                return getDefault()

            return when (current) {
                LOOP -> LIST
                LIST -> SHUFFLE
                SHUFFLE -> SINGLE
                SINGLE -> LOOP
            }
        }
    }
}