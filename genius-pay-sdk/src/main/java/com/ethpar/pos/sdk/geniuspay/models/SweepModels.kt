package com.ethpar.pos.sdk.geniuspay.models

import kotlinx.serialization.Serializable

@Serializable
data class SweepRequest(
    val walletAddress: String,
    val privateKey: String,
    val merchantId: String
)

@Serializable
data class SweepResponse(
    val status: String,
    val amountSwept: String,
    val merchantBalance: String,
    val transactionId: String,
    val blockchainTxHash: String
)
