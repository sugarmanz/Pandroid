package com.jeremiahzucker.pandroid

import com.jeremiahzucker.pandroid.extensions.log
import kotlinx.coroutines.delay
import java.net.URL
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine

suspend fun main() = with<PandoraSdk, Unit>(PandoraSdk()) {
    authenticate(
        "zucker.jeremiah+pandroid2@gmail.com",
        "pencil",
    )

    val stationToken = getStations().first().stationToken!!

    val tracks = getPlaylist(stationToken).log()
    val url = tracks.first().audioUrlMap?.highQuality?.audioUrl.log()

    MediaPlayer

    // AudioSystem.getAudioFileTypes().map { it.extension }.log()
    // // URL(url).content.log()
    // //
    // // val audioInputStream = AudioSystem.getAudioInputStream(URL(url))
    // //
    // // // create clip reference
    // // val clip = AudioSystem.getClip()
    // //
    // // // open audioInputStream to the clip
    // // clip.open(audioInputStream)
    // //
    // // clip.loop(Clip.LOOP_CONTINUOUSLY)
    //
    // val `in` = AudioSystem.getAudioInputStream(URL(url))
    // val baseFormat = `in`.format
    // val decodedFormat = AudioFormat(
    //     AudioFormat.Encoding.PCM_SIGNED,
    //     baseFormat.sampleRate, 16, baseFormat.channels,
    //     baseFormat.channels * 2, baseFormat.sampleRate,
    //     false
    // )
    // val din = AudioSystem.getAudioInputStream(decodedFormat, `in`)
    // val info = DataLine.Info(SourceDataLine::class.java, decodedFormat)
    // val line = AudioSystem.getLine(info) as? SourceDataLine
    // if (line != null) {
    //     line.open(decodedFormat)
    //     val data = ByteArray(4096)
    //     // Start
    //     line.start()
    //     var nBytesRead: Int
    //     while (din.read(data, 0, data.size).also { nBytesRead = it } != -1) {
    //         line.write(data, 0, nBytesRead)
    //     }
    //     // Stop
    //     line.drain()
    //     line.stop()
    //     line.close()
    //     din.close()
    // }

    delay(500)
}
