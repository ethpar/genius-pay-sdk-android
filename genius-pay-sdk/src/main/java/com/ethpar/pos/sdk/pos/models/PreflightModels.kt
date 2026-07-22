package com.ethpar.pos.sdk.pos.models

import kotlinx.serialization.Serializable

@Serializable
data class PreflightRequest(
    val totalWithFees: String,
    val totalSale: String,
    val merchantJwt: String,
    val terminalId: String
)

@Serializable
data class PreflightResponse(
    val preflightId: String,
    val status: String,
    val reason: String? = null
)
