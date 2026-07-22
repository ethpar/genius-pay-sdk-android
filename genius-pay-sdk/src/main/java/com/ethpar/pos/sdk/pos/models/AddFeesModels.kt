package com.ethpar.pos.sdk.pos.models

import com.ethpar.pos.sdk.util.NumberAsFloatSerializer
import kotlinx.serialization.Serializable

@Serializable
data class AddFeesRequest(
    val totalSale: String
)

@Serializable
data class AddFeesResponse(
    @Serializable(with = NumberAsFloatSerializer::class) val totalSale: Float,
    @Serializable(with = NumberAsFloatSerializer::class) val convenienceFee: Float,
    @Serializable(with = NumberAsFloatSerializer::class) val processingFee: Float,
    @Serializable(with = NumberAsFloatSerializer::class) val totalWithFees: Float
)
