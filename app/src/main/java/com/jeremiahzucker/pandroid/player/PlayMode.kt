package com.jeremiahzucker.pandroid.player

/**
 * Created by sugarmanz on 9/10/17.
 */
enum class PlayMode {
    SINGLE,
    LOOP,
    LIST,
    SHUFFLE;
    // TODO: I'm pretty sure we only need single and list

    companion object {
        val default: PlayMode = SINGLE
        fun nextMode(curr: PlayMode?): PlayMode =
            when (curr) {
                SINGLE -> LOOP
                LOOP -> LIST
                LIST -> SHUFFLE
                else -> default
            }
    }
}