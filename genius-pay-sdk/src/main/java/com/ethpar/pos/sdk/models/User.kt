package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val phone: String?,
    val name: String?,
    val publicAddress: String?,
    val createdOn: String,
    val updatedOn: String
)
