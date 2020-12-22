package com.jeremiahzucker.pandroid

import com.jeremiahzucker.pandroid.cache.DatabaseDriverFactory
import com.jeremiahzucker.pandroid.extensions.log
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent

val pandoraSdk = PandoraSdk(DatabaseDriverFactory())

suspend fun main() {
    pandoraSdk.authenticate(
        "zucker.jeremiah+pandroid2@gmail.com",
        "pencil",
    )

    val stationToken = pandoraSdk.getStations().first().stationToken

    val tracks = pandoraSdk.getPlaylist(stationToken).log()
    val url = tracks.first().audioUrlMap?.highQuality?.audioUrl.log()

    val mediaPlayer = AudioPlayerComponent()
    mediaPlayer.mediaPlayer().media().play(url)
}
