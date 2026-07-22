package com.ethpar.pos.sdk.pos.models

import kotlinx.serialization.Serializable

@Serializable
data class AddFeesRequest(
    val totalSale: String
)

@Serializable
data class AddFeesResponse(
    val totalSale: Float,
    val convenienceFee: Float,
    val processingFee: Float,
    val totalWithFees: Float
)
