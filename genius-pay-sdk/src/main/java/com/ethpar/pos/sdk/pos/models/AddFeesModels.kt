package com.ethpar.pos.sdk.pos.models

import kotlinx.serialization.Serializable

@Serializable
data class AddFeesRequest(
    val totalSale: String
)

@Serializable
data class AddFeesResponse(
    val totalSale: String,
    val convenienceFee: String,
    val processingFee: String,
    val totalWithFees: String
)
