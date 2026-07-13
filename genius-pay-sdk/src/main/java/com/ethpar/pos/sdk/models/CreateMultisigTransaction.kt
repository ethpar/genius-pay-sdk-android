package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateMultisigTransactionRequest(
    val walletId: String,
    val assetType: String,
    val to: String,
    val amount: String,
    val tokenAddress: String?,
    val remark: String?
)
