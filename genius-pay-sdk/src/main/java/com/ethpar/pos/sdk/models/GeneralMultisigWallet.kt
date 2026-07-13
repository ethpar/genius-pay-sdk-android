package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class GeneralMultisigWallet(
    val id: String,
    val name: String,
    val userId: String,
    val wallet: MultisigWallet,
    val createdOn: String,
    val creatorId: String,
    val type: String,
    val signerIds: List<String?>,
    val signerAddresses: List<String>
)
