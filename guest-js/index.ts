import { invoke } from '@tauri-apps/api/core'

export async function ping(value: string): Promise<string | null> {
  return await invoke<{value?: string}>('plugin:videoplayer|ping', {
    payload: {
      value,
    },
  }).then((r) => (r.value ? r.value : null));
}

export async function playVideo(path: string): Promise<void> {
  await invoke('plugin:videoplayer|play_video', {
    payload: {
      path
    }
  });
}