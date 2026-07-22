package com.ethpar.pos.sdk.wallet.models

import kotlinx.serialization.Serializable

@Serializable
data class GenerateLoginCodeRequest(
    val contact: String,
    val password: String? = null
)
