package com.ethpar.pos.sdk.wallet.models

import kotlinx.serialization.Serializable

@Serializable
data class FeatureFlags(
    val multisignatureWallet: Boolean,
    val paymentWallet: Boolean
)
