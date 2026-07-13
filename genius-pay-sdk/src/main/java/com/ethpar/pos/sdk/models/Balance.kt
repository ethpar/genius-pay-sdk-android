package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class Balance(
    val amount: String,
    val tokenAddress: String?,
    val currentPrice: Double,
    val priceChangePercentange24h: Double
)
