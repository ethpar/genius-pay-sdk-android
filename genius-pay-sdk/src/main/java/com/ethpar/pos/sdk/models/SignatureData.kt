package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class SignatureData(
    val txid: String,
    val address: String,
    val signature: String
)
