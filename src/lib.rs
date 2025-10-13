use tauri::{
  plugin::{Builder, TauriPlugin},
  Manager, Runtime,
};

pub use models::*;

#[cfg(desktop)]
mod desktop;
#[cfg(mobile)]
mod mobile;

mod commands;
mod error;
mod models;

pub use error::{Error, Result};

#[cfg(desktop)]
use desktop::Videoplayer;
#[cfg(mobile)]
use mobile::Videoplayer;

/// Extensions to [`tauri::App`], [`tauri::AppHandle`] and [`tauri::Window`] to access the videoplayer APIs.
pub trait VideoplayerExt<R: Runtime> {
  fn videoplayer(&self) -> &Videoplayer<R>;
}

impl<R: Runtime, T: Manager<R>> crate::VideoplayerExt<R> for T {
  fn videoplayer(&self) -> &Videoplayer<R> {
    self.state::<Videoplayer<R>>().inner()
  }
}

/// Initializes the plugin.
pub fn init<R: Runtime>() -> TauriPlugin<R> {
  Builder::new("videoplayer")
    .invoke_handler(tauri::generate_handler![commands::ping, commands::play_video, commands::force_focus])
    .setup(|app, api| {
      #[cfg(mobile)]
      let videoplayer = mobile::init(app, api)?;
      #[cfg(desktop)]
      let videoplayer = desktop::init(app, api)?;
      app.manage(videoplayer);
      Ok(())
    })
    .build()
}
