package com.yeonv.videoplayer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class VideoActivity : AppCompatActivity() {
    companion object {
        // A constant for the key to avoid typos
        const val VIDEO_PATH_EXTRA = "videoPath"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        // Make the activity fullscreen
        hideSystemUI()

        // Get the path passed from our plugin
        val path = intent.getStringExtra(VIDEO_PATH_EXTRA)

        // Find the TextView in our layout and set its text
        val textView = findViewById<TextView>(R.id.videoPathTextView)
        textView.text = path
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}