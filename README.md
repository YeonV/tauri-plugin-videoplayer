# Tauri Plugin: Native Video Player

 [![crate](https://img.shields.io/crates/v/tauri-plugin-videoplayer.svg?logo=rust&logoColor=white)](https://crates.io/crates/tauri-plugin-videoplayer) [![npm](https://img.shields.io/npm/v/tauri-plugin-videoplayer-api?logo=npm&logoColor=white)](https://www.npmjs.com/package/tauri-plugin-videoplayer-api) [![license](https://img.shields.io/crates/l/tauri-plugin-videoplayer?logo=github&logoColor=white)](https://github.com/YeonV/tauri-plugin-videoplayer/blob/main/LICENSE) <br/> [![tauri-v2](https://img.shields.io/badge/Tauri-v2.0-blue.svg?logo=tauri&logoColor=white)](https://v2.tauri.app/) [![creator](https://img.shields.io/badge/CREATOR-Yeon-blue.svg?logo=github&logoColor=white)](https://github.com/YeonV) [![creator](https://img.shields.io/badge/A.K.A-Blade-darkred.svg?logo=github&logoColor=white)](https://github.com/YeonV)

A Tauri v2 plugin that provides a native video player, launched from your webview. Ideal for playing fullscreen video streams (like M3U8) on mobile devices without the overhead or limitations of a web-based player.

---

### 🤔 Why This Plugin?

Web-based video players are fantastic, but they can struggle on mobile, especially with certain stream formats or when dealing with performance-intensive tasks. This plugin solves that by handing off the video playback to the operating system's native player.

On Android, this means launching a new, fullscreen `Activity` powered by `androidx.media3` (the successor to ExoPlayer), the same powerful media engine used by apps like YouTube and Netflix.

The core benefit is **performance and stability**. It also ensures a clean separation of concerns and robust lifecycle management—the stream is **guaranteed to terminate** when the user switches apps, a critical feature for single-stream services.

### ✨ Features

-   ✅ **Native Android Playback**: Utilizes a fullscreen `Activity` with `androidx.media3` (ExoPlayer).
-   ✅ **Simple API**: A single `playVideo(url)` function to launch the player.
-   ✅ **HLS/M3U8 Support**: Built to handle streaming playlists out of the box.
-   ✅ **Robust Lifecycle Management**: Automatically stops and releases the player when the user navigates away (e.g., switches app, goes to home screen), ensuring stream connections are properly terminated.
-   ✅ **Tauri v2 Ready**: Built from the ground up for the modern Tauri v2 architecture, including the new permission system.

### 📦 Compatibility

This plugin is currently in its early stages and supports:

| Platform | Supported |
| :------- | :-------: |
| Android  |    ✅     |
| iOS      |    ❌     |
| Windows  |    ❌     |
| macOS    |    ❌     |
| Linux    |    ❌     |

This plugin is only compatible with **Tauri v2**.

### 🛠️ Installation

Integrating the plugin into your Tauri v2 application involves four steps:

**1. Install the JavaScript Package:**

```bash
# Using npm
npm install tauri-plugin-videoplayer-api

# Using yarn
yarn add tauri-plugin-videoplayer-api
```

**2. Add the Rust Crate:**

Add the crate to your app's `src-tauri/Cargo.toml` dependencies:
```toml
[dependencies]
# ... other dependencies
tauri-plugin-videoplayer = "0.1.0"
```

**3. Register the Plugin:**

In your app's `src-tauri/src/lib.rs` (or `main.rs`), register the plugin with the Tauri builder:

```rust
#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .plugin(tauri_plugin_videoplayer::init()) // <-- Add this line
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
```

**4. Configure Permissions:**

The plugin requires permission to be called from the frontend. Add `"videoplayer:default"` to your app's capabilities file at `src-tauri/capabilities/default.json`:

```json
{
  // ...
  "permissions": [
    "core:default",
    "videoplayer:default" // <-- Add this line
  ]
}
```
> **Note:** If you're adding the plugin to a new project, your IDE might show an error on this line initially. Run `yarn tauri dev` once to force Tauri to regenerate its permission schemas, and the error will disappear.

### 🚀 Usage

Using the plugin in your frontend code is straightforward. Import the `playVideo` function and call it with a valid video URL.

```typescript
import { playVideo } from 'tauri-plugin-videoplayer-api';

function MyComponent() {
  const handlePlayButtonClick = () => {
    const m3u8_url = 'https://your-stream-provider.com/stream.m3u8';
    console.log(`Requesting native playback for: ${m3u8_url}`);
    
    // This will launch the native fullscreen player on Android
    playVideo(m3u8_url);
  };

  return (
    <button onClick={handlePlayButtonClick}>
      Play Video Natively
    </button>
  );
}
```

On Android, this will launch the fullscreen native player. The user can return to your app by using the standard system back button.
