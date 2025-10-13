use serde::de::DeserializeOwned;
use tauri::{plugin::PluginApi, AppHandle, Runtime, Manager};

use crate::models::*;

pub fn init<R: Runtime, C: DeserializeOwned>(
  app: &AppHandle<R>,
  _api: PluginApi<R, C>,
) -> crate::Result<Videoplayer<R>> {
  Ok(Videoplayer(app.clone()))
}

/// Access to the videoplayer APIs.
pub struct Videoplayer<R: Runtime>(AppHandle<R>);

impl<R: Runtime> Videoplayer<R> {
  pub fn ping(&self, payload: PingRequest) -> crate::Result<PingResponse> {
    Ok(PingResponse {
      value: payload.value,
    })
  }

  pub fn play_video(&self, payload: PlayVideoRequest) -> crate::Result<()> {
    println!("[Desktop] Playing video from: {}", payload.path);
    Ok(())
  }

  // The signature changes, but the logic doesn't use the payload.
  pub fn force_focus(&self, _payload: ForceFocusRequest) -> crate::Result<()> {
      if let Some(window) = self.0.get_webview_window("main") {
          window.set_focus().map_err(Into::into)
      } else {
          Ok(())
      }
  }
}
