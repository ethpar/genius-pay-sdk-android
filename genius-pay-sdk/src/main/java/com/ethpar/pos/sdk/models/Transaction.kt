package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val id: String,
    val assetType: String,
    val asset: TransactionAsset,
    val amount: String,
    val to: String,
    val walletId: String,
    val initiatorUserId: String?,
    val executorAddress: String?,
    val signatures: List<Signature>,
    val status: String,
//    val data: SimpleMultisigTransactionData,
    val hash: String?,
    val fee: FeeQuote,
    val createdOn: String,
    val updatedOn: String,
    val remark: String?,
    val type: String,
    val merchant: String?
)

@Serializable
data class TransactionAsset(
    val tokenAddress: String?,
    val symbol: String,
    val decimals: Int,
    val image: String?
)

@Serializable
data class FeeQuote(
    val gasLimit: String,
    val maxFeePerGas: String? = null,
    val maxPriorityFeePerGas: String? = null,
    val gasPrice: String? = null,
    val totalWei: String? = null
)

@Serializable
data class Signature(
    val address: String,
    val signature: String
)
