package com.ethpar.pos.sdk.pos.models

import com.ethpar.pos.sdk.util.NumberAsFloatSerializer
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
    @Serializable(with = NumberAsFloatSerializer::class) val amountSwept: Float,
    @Serializable(with = NumberAsFloatSerializer::class) val merchantBalance: Float,
    val transactionId: Int,
    val blockchainTxHash: String
)
