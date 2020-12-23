package com.jeremiahzucker.pandroid

import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.key.ExperimentalKeyInput
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.shortcuts
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.jeremiahzucker.pandroid.cache.DatabaseDriverFactory
import com.jeremiahzucker.pandroid.cache.Preferences
import com.jeremiahzucker.pandroid.extensions.log
import com.jeremiahzucker.pandroid.models.Response
import com.jeremiahzucker.pandroid.models.TrackModel
import kotlinx.coroutines.launch
import org.jetbrains.skija.Image.*
import uk.co.caprica.vlcj.media.MediaRef
import uk.co.caprica.vlcj.media.TrackType
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent
import java.net.URL

val pandoraSdk = PandoraSdk(DatabaseDriverFactory())
val mediaPlayer = AudioPlayerComponent()

@ExperimentalKeyInput
@Composable
fun Auth(handleAuth: suspend (String, String) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val (username, setUsername) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }

    Column(Modifier.padding(8.dp), Arrangement.spacedBy(5.dp, Alignment.CenterVertically)) {
        TextField(
            value = username,
            onValueChange = setUsername,
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
                .shortcuts {
                    on(Key.Tab) {
                        // TODO: Setup focus change
                    }
                    on(Key.Enter) {}
                },
            leadingIcon = { Icon(Icons.Filled.AccountCircle) },
        )
        TextField(
            value = password,
            onValueChange = setPassword,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
                .shortcuts {
                    on(Key.Enter) {
                        coroutineScope.launch { handleAuth(username, password) }
                    } },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Filled.Lock) },
        )
        Button(modifier = Modifier.fillMaxWidth(), onClick = { coroutineScope.launch { handleAuth(username, password) } }) {
            Text("Sign In")
        }
    }
}

@Composable
fun Muzak(trackModel: TrackModel, next: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    val (isPlaying, setIsPlaying) = remember {
        mutableStateOf(mediaPlayer.mediaPlayer().status().isPlaying)
    }

    val (currentTrack, setCurrentTrack) = remember { mutableStateOf<TrackModel?>(null) }
    val (image, setImage) = remember { mutableStateOf<ImageBitmap?>(null) }

    if (currentTrack != trackModel) {
        setCurrentTrack(trackModel)
        coroutineScope.launch {
            mediaPlayer.mediaPlayer().media().play(trackModel.audioUrlMap!!.highQuality.audioUrl)

            URL(trackModel.albumArtUrl!!).readBytes()
                .let(::makeFromEncoded)
                .asImageBitmap()
                .let(setImage)
        }
    }


    mediaPlayer.mediaPlayer().events().addMediaPlayerEventListener(object : MediaPlayerEventListener {
        override fun mediaChanged(mediaPlayer: MediaPlayer?, media: MediaRef?) = Unit

        override fun opening(mediaPlayer: MediaPlayer?) = Unit

        override fun buffering(mediaPlayer: MediaPlayer?, newCache: Float) = Unit

        override fun playing(mediaPlayer: MediaPlayer?) {
            mediaPlayer?.submit {
                setIsPlaying(true)
            }
        }

        override fun paused(mediaPlayer: MediaPlayer?) {
            mediaPlayer?.submit {
                setIsPlaying(false)
            }
        }

        override fun stopped(mediaPlayer: MediaPlayer?) {
            mediaPlayer?.submit {
                setIsPlaying(false)
            }
        }

        override fun forward(mediaPlayer: MediaPlayer?) = Unit

        override fun backward(mediaPlayer: MediaPlayer?) = Unit

        override fun finished(mediaPlayer: MediaPlayer?) {
            mediaPlayer?.submit(next)
        }

        override fun timeChanged(mediaPlayer: MediaPlayer?, newTime: Long) = Unit

        override fun positionChanged(mediaPlayer: MediaPlayer?, newPosition: Float) = Unit

        override fun seekableChanged(mediaPlayer: MediaPlayer?, newSeekable: Int) = Unit

        override fun pausableChanged(mediaPlayer: MediaPlayer?, newPausable: Int) = Unit

        override fun titleChanged(mediaPlayer: MediaPlayer?, newTitle: Int) = Unit

        override fun snapshotTaken(mediaPlayer: MediaPlayer?, filename: String?) = Unit

        override fun lengthChanged(mediaPlayer: MediaPlayer?, newLength: Long) = Unit

        override fun videoOutput(mediaPlayer: MediaPlayer?, newCount: Int) = Unit

        override fun scrambledChanged(mediaPlayer: MediaPlayer?, newScrambled: Int) = Unit

        override fun elementaryStreamAdded(mediaPlayer: MediaPlayer?, type: TrackType?, id: Int) = Unit

        override fun elementaryStreamDeleted(mediaPlayer: MediaPlayer?, type: TrackType?, id: Int) = Unit

        override fun elementaryStreamSelected(mediaPlayer: MediaPlayer?, type: TrackType?, id: Int) = Unit

        override fun corked(mediaPlayer: MediaPlayer?, corked: Boolean) = Unit

        override fun muted(mediaPlayer: MediaPlayer?, muted: Boolean) = Unit

        override fun volumeChanged(mediaPlayer: MediaPlayer?, volume: Float) = Unit

        override fun audioDeviceChanged(mediaPlayer: MediaPlayer?, audioDevice: String?) = Unit

        override fun chapterChanged(mediaPlayer: MediaPlayer?, newChapter: Int) = Unit

        override fun error(mediaPlayer: MediaPlayer?) = Unit

        override fun mediaPlayerReady(mediaPlayer: MediaPlayer?) = Unit
    })

    Column(Modifier.padding(8.dp), Arrangement.spacedBy(5.dp)) {
        Text("Now Playing:")

        image?.let {
            Image(image,
                modifier = Modifier
                    .fillMaxWidth()
                    .preferredHeight(350.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }

        currentTrack?.songName?.let { Text(it, fontWeight = FontWeight.Bold) }
        currentTrack?.artistName?.let { Text(it) }

        Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(5.dp)) {
            Button(modifier = Modifier.fillMaxWidth(0.5F), onClick = {
                mediaPlayer.mediaPlayer().submit {
                    if (isPlaying) mediaPlayer.mediaPlayer()
                        .controls()
                        .pause()
                    else mediaPlayer.mediaPlayer()
                        .controls()
                        .play()
                }
            }) {
                Text(if (isPlaying) "Pause" else "Play")
            }

            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                mediaPlayer.mediaPlayer().submit {
                    mediaPlayer.mediaPlayer().controls().stop()
                }

                next()
            }) {
                Text("Next")
            }
        }
    }
}

@ExperimentalKeyInput
fun main() = Window(title = "Pandroid", size = IntSize(400, 530)) {
    val coroutineScope = rememberCoroutineScope()
    val (authenticated, setAuthenticated) = remember { mutableStateOf(Preferences.partnerAuthToken != null) }
    val (tracks, setTracks) = remember { mutableStateOf(emptyList<TrackModel>()) }

    MaterialTheme {
        if (!authenticated && Preferences.username != null && Preferences.password != null) {
            coroutineScope.launch {
                pandoraSdk.authenticate(Preferences.username!!, Preferences.password!!)
                setAuthenticated(true)
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(progress = 0.5f)
            }
        } else if (!authenticated) {
            Auth { username, password ->
                pandoraSdk.authenticate(username, password)
                setAuthenticated(true)
            }
        } else if (tracks.isEmpty()) {
            // TODO: Choose station
            coroutineScope.launch {
                try {
                    val stationToken = pandoraSdk.getStations().first().stationToken
                    pandoraSdk.getPlaylist(stationToken).filter { it.trackToken != null }.let(setTracks)
                } catch (exception: Response.ResponseFailedException) {
                    if (exception.failure.code == 1001) {
                        setAuthenticated(false)
                    } else {
                        throw exception
                    }
                }
            }
        } else {
            Muzak(tracks.first()) {
                setTracks(tracks.drop(1))
            }
        }
    }
}

// suspend fun getMuzak(): TrackModel = pandoraSdk.run {
//     authenticate()
// }

suspend fun playMuzak() {
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
