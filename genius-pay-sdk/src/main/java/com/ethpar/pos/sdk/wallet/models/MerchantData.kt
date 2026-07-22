package com.ethpar.pos.sdk.wallet.models

data class MerchantData(
    val name: String,
    val receivingAddress: String,
    val tokenAddress: String?,
    val tokenDecimals: Int
)