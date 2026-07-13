package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class FeatureFlags(
    val multisignatureWallet: Boolean,
    val paymentWallet: Boolean
)
