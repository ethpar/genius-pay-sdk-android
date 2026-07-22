package com.ethpar.pos.sdk.pos.models

import kotlinx.serialization.Serializable

@Serializable
data class EnrollmentRequest(
    val terminalSerialNumber: String,
    val merchantId: String,
    val activationCode: String
)

@Serializable
data class EnrollmentResponse(
    val signingKeyId: String,
    val signingKeySecret: String,
    val tokenEndpoint: String,
    val merchantName: String
)
