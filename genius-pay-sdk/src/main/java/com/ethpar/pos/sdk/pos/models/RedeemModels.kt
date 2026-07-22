package com.ethpar.pos.sdk.pos.models

import kotlinx.serialization.Serializable

@Serializable
data class RedeemRequest(
    val merchantId: String,
    val amount: String,
    val bankAccountId: String
)

@Serializable
data class RedeemResponse(
    val status: String,
    val redemptionId: String,
    val amountRedeemed: String,
    val fiatAmount: String,
    val estimatedSettlement: String,
    val merchantBalance: String
)
