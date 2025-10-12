<script>
  import { invoke } from "@tauri-apps/api/core";
  // Import our plugin's functions
  import { ping, playVideo } from "tauri-plugin-videoplayer-api";

  let name = $state("");
  let greetMsg = $state("");
  // Add state for our new video path input
  let videoPath = $state("/movies/my-favorite-video.mp4");

  async function doGreet(){
    // This is your original greet function
    greetMsg = await invoke("greet", { name });
  }

  // This function calls the plugin's ping command
  async function doPing() {
    greetMsg = await ping(name) ?? "pong";
  }

  // --- Our New Function ---
  // This function calls the plugin's new playVideo command
  async function callPlayVideo() {
    if (!videoPath) {
      alert("Please enter a video path.");
      return;
    }
    console.log(`[Frontend] Calling playVideo with path: ${videoPath}`);
    await playVideo(videoPath);
    alert(`Called the native playVideo function with path: ${videoPath}\n\nCheck your Rust console (for desktop) or Android logcat (for mobile) for the output.`);
  }
</script>

<div>
  <div class="row">
    <input id="greet-input" placeholder="Enter a name..." bind:value={name} />
    <button onclick={doGreet}>
      Greet
    </button>
    <!-- Add the Ping button -->
    <button onclick={doPing}>
      Ping
    </button>
  </div>

  <!-- Add our new UI elements -->
  <div class="row">
    <input id="video-path-input" placeholder="Enter a video path..." bind:value={videoPath} />
    <button onclick={callPlayVideo}>Play Video</button>
  </div>
  
  <p>{greetMsg}</p>
</div>