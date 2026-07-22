package com.ethpar.pos.sdk.pos.models

import kotlinx.serialization.Serializable

@Serializable
data class MerchantAddressRequest(
    val merchantId: String
)

@Serializable
data class MerchantAddressResponse(
    val merchantAddress: String,
    val network: String,
    val tokenContract: String,
    val merchantName: String
)
