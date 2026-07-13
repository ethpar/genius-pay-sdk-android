package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class PosTransaction(
    val amount: String,
    val destination: String,
    val merchant: String,
    val remark: String,
    val panHash: String,
    val confirmations: Int,
    val assetType: String?,
    val tokenAddress: String?
)