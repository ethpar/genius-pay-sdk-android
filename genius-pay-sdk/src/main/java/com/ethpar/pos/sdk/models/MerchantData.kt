package com.ethpar.pos.sdk.models

data class MerchantData(
    val name: String,
    val receivingAddress: String,
    val tokenAddress: String?,
    val tokenDecimals: Int
)