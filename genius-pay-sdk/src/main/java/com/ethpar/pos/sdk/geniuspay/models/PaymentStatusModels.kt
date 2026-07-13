package com.ethpar.pos.sdk.geniuspay.models

import kotlinx.serialization.Serializable

@Serializable
data class PaymentStatusRequest(
    val merchantId: String,
    val txHash: String? = null
)

@Serializable
data class PaymentStatusResponse(
    val status: String,
    val confirmations: Int,
    val ethparFinality: Boolean,
    val amount: String,
    val fromAddress: String,
    val txHash: String,
    val timestamp: String
)
