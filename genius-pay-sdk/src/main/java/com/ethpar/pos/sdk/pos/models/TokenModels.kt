package com.ethpar.pos.sdk.pos.models

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    val client_assertion_type: String,
    val grant_type: String,
    val client_assertion: String,
    val client_id: String
)

@Serializable
data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)
