package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class SetUserPublicAddressRequest(
    val address: String
)
