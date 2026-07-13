package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class GenerateLoginCodeRequest(
    val contact: String,
    val password: String? = null
)
