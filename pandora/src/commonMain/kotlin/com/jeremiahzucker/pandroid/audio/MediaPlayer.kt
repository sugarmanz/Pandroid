package com.jeremiahzucker.pandroid.audio

import io.ktor.http.Url

interface MediaPlayer {

    fun start(url: Url)

    fun play()

    fun pause()

}

