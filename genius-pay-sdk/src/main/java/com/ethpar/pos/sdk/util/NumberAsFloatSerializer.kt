package com.ethpar.pos.sdk.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.float

object NumberAsFloatSerializer : KSerializer<Float> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("NumberAsFloat", PrimitiveKind.FLOAT)

    override fun deserialize(decoder: Decoder): Float {
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw IllegalStateException("This serializer only works with JSON")
        val element = jsonDecoder.decodeJsonElement() as? JsonPrimitive
            ?: throw IllegalArgumentException("Expected a primitive number")

        return when {
            element.isString -> element.content.toFloat()
            else -> element.float
        }
    }

    override fun serialize(encoder: Encoder, value: Float) {
        encoder.encodeFloat(value)
    }
}
