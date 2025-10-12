use serde::{Deserialize, Serialize};

#[derive(Debug, Deserialize, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct PingRequest {
  pub value: Option<String>,
}

#[derive(Debug, Clone, Default, Deserialize, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct PingResponse {
  pub value: Option<String>,
}

#[derive(Debug, Deserialize, Serialize)]
#[serde(rename_all = "camelCase")]
pub struct PlayVideoRequest {
    pub path: String,
}

// #[derive(Debug, Clone, Default, Deserialize, Serialize)]
// #[serde(rename_all = "camelCase")]
// pub struct PlayVideoResponse {
//     pub success: bool,
//     pub message: Option<String>,
// }
