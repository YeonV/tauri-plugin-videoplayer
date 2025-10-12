package com.yeonv.videoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class VideoActivity : AppCompatActivity() {
    companion object {
        const val VIDEO_PATH_EXTRA = "videoPath"
    }

    // 1. Declare Player and View variables
    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        // Find the PlayerView from our layout
        playerView = findViewById(R.id.player_view)

        // Make the activity fullscreen
        hideSystemUI()
    }

    // 2. Initialize the player in onStart()
    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    // 3. Release the player in onStop()
    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {
        // Get the M3U8 URL passed from our plugin
        val videoUrl = intent.getStringExtra(VIDEO_PATH_EXTRA)
        if (videoUrl == null) {
            // If there's no URL, we can't do anything.
            finish()
            return
        }

        // Create an ExoPlayer instance
        player = ExoPlayer.Builder(this).build()
        
        // Attach the player to the view
        playerView.player = player

        // Create a MediaItem from the URL
        val mediaItem = MediaItem.fromUri(videoUrl)

        // Set the media item to be played
        player?.setMediaItem(mediaItem)

        // Prepare the player
        player?.prepare()

        // Start playback automatically when ready
        player?.playWhenReady = true
    }

    private fun releasePlayer() {
        player?.let {
            it.release() // This is a crucial step to free up resources.
            player = null
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}