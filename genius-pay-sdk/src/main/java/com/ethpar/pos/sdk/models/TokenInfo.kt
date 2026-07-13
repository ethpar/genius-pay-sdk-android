package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class TokenInfo(
    val tokenAddress: String?,
    val imageUrl: String,
    val token: Token
)

@Serializable
data class Token(
    val name: String,
    val decimals: Int,
    val symbol: String
)
