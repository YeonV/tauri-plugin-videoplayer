package com.yeonv.videoplayer

import android.app.Activity
import android.util.Log
import android.content.Intent
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke

@InvokeArg
class PingArgs {
  var value: String? = null
}

@InvokeArg
class PlayVideoArgs {
  lateinit var path: String
}

@TauriPlugin
class ExamplePlugin(private val activity: Activity): Plugin(activity) {
  private val implementation = Example()

  @Command
  fun ping(invoke: Invoke) {
      val args = invoke.parseArgs(PingArgs::class.java)

      val ret = JSObject()
      ret.put("value", implementation.pong(args.value ?: "default value :("))
      invoke.resolve(ret)
  }

  @Command
  fun playVideo(invoke: Invoke) {
    val args = invoke.parseArgs(PlayVideoArgs::class.java)
    
    // Create an Intent to launch our new VideoActivity
    val intent = Intent(activity, VideoActivity::class.java).apply {
        // Pass the video path to the activity
        putExtra(VideoActivity.VIDEO_PATH_EXTRA, args.path)
    }

    // Launch the activity
    activity.startActivity(intent)

    // Signal back to Tauri that the command succeeded
    invoke.resolve()
  }
}