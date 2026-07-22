package com.ethpar.pos.sdk.pos.models

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationRequest(
    val preflightId: String,
    val networkTrackingId: String,
    val approvalCode: String,
    val totalSale: String,
    val totalWithFees: String,
    val maskedPan: String,
    val cardholderName: String? = null,
    val expirationDate: String,
    val entryMode: String,
    val cardBrand: String,
    val aid: String? = null,
    val applicationLabel: String? = null
)

@Serializable
data class ConfirmationResponse(
    val walletAddress: String,
    val walletPrivateKey: String,
    val stablecoinAmount: String,
    val receiptHeader: String,
    val receiptFooter: String,
    val transactionId: String
)
