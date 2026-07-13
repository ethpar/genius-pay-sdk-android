package com.ethpar.pos.sdk.models

import kotlinx.serialization.Serializable

@Serializable
data class BinInfo(
    val number: BinNumber? = null,
    val scheme: String? = null,
    val type: String? = null,
    val brand: String? = null,
    val country: BinCountry? = null,
    val bank: BinBank? = null
)

@Serializable
data class BinNumber(
    val length: Int,
    val luhn: Boolean
)

@Serializable
data class BinCountry(
    val numeric: String,
    val alpha2: String,
    val name: String,
    val emoji: String,
    val currency: String,
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class BinBank(
    val name: String,
    val url: String? = null,
    val phone: String? = null,
    val city: String? = null
)
