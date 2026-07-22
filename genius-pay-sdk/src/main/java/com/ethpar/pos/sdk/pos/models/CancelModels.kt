package com.ethpar.pos.sdk.pos.models

import kotlinx.serialization.Serializable

@Serializable
data class CancelRequest(
    val preflightId: String,
    val networkTrackingId: String? = null,
    val rejectionCode: String,
    val rejectionReason: String
)

@Serializable
data class CancelResponse(
    val status: String,
    val preflightId: String
)
