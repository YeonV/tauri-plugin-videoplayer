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

@InvokeArg
class ForceFocusArgs {
  lateinit var mainActivityClassName: String // <-- Matches the field in ForceFocusRequest
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

   @Command
    fun forceFocus(invoke: Invoke) {
        try {
            // Get the arguments from the payload
            val args = invoke.parseArgs(ForceFocusArgs::class.java)
            
            // Use the class name passed from the webview
            val mainActivityClass = Class.forName(args.mainActivityClassName)
            
            val intent = Intent(activity, mainActivityClass).apply {
                addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            }
            
            activity.startActivity(intent)
            Log.d("VideoplayerPlugin", "Forcing focus to main activity: ${args.mainActivityClassName}")
            invoke.resolve()
        } catch (e: Exception) {
            invoke.reject("Failed to force focus: ${e.message}")
        }
    }
}