package com.ethpar.pos.sdk.pos

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ethpar.pos.sdk.pos.models.EnrollmentResponse
import java.util.Date

internal fun buildClientAssertion(enrollment: EnrollmentResponse): String {
    val now = Date()
    val algorithm = Algorithm.HMAC256(enrollment.signingKeySecret.hexToBytes())
    return JWT.create()
        .withIssuer(enrollment.signingKeyId)
        .withSubject(enrollment.signingKeyId)
        .withAudience(enrollment.tokenEndpoint)
        .withIssuedAt(now)
        .withExpiresAt(Date(now.time + 300_000))
        .sign(algorithm)
}

fun String.hexToBytes(): ByteArray =
    chunked(2)
        .map { byteHex -> byteHex.toInt(16).toByte() }
        .toByteArray()
