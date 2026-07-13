package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class GetBalancesRequest(
    val tokens: List<TokenAddress>
)

@Serializable
data class TokenAddress(
    val tokenAddress: String?
)
