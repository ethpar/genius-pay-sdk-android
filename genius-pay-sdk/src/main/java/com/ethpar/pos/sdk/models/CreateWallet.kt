package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateCCWallet(
    val pan: String
)

@Serializable
data class CreateMultisigWalletRequest(
    val name: String,
    val signers: List<String>,
    val threshold: Int
)
