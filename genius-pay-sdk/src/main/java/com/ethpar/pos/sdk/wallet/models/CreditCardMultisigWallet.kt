package com.ethpar.pos.sdk.wallet.models

import kotlinx.serialization.Serializable

@Serializable
data class MultisigWallet(
    val address: String,
    val m: Int,
    val n: Int,
    val executorAddress: String?,
    val signers: List<Signer>,
    val creationHash: String?
)

@Serializable
data class Signer(
    val id: String,
    val address: String,
    val userId: String,
    val acceptedOn: String
)

@Serializable
data class CreditCardMultisigWallet(
    val id: String,
    val name: String,
    val userId: String,
    val wallet: MultisigWallet,
    val createdOn: String,
    val panLastDigits: String,
    val panHash: String,
    val binHash: String,
    val binInfo: BinInfo?,
    val type: String
)
