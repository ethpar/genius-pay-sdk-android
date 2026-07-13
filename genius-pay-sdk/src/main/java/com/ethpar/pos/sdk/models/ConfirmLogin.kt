package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmLoginCodeRequest(
    val contact: String,
    val code: String
)

@Serializable
data class ConfirmLoginCodeResponse(
    val sessionKey: String
)
