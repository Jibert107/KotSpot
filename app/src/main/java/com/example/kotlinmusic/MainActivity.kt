package com.example.kotlinmusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.kotlinmusic.ui.theme.KotlinMusicTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.RawResourceDataSource

class MainActivity : ComponentActivity() {
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = ExoPlayer.Builder(this).build()

        setContent {
            KotlinMusicTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        Button(onClick = {
                            val uri = RawResourceDataSource.buildRawResourceUri(R.raw.grisaille)
                            player.setMediaItem(MediaItem.fromUri(uri))
                            player.prepare()
                            player.play()
                        }) {
                            Text("Play Music File")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        PlayerViewComposable(player)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}

@Composable
fun PlayerViewComposable(player: ExoPlayer, modifier: Modifier = Modifier) {
    AndroidView(factory = { context ->
        PlayerView(context).apply {
            this.player = player
        }
    }, modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun PlayerViewPreview() {
    val mockPlayer = ExoPlayer.Builder(LocalContext.current).build()
    KotlinMusicTheme {
        PlayerViewComposable(player = mockPlayer)
    }
}