package com.jeremiahzucker.pandroid.models

import com.jeremiahzucker.pandroid.network.PandoraApi
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.capturedKClass
import kotlinx.serialization.descriptors.mapSerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import java.lang.Exception

@Serializable(with = Response.PolySerializer::class)
sealed class Response<T> {

    class ResponseFailedException(val failure: Failure<*>) : Exception(failure.toString())

    val success: Success<T> get() = when (this) {
        is Success<T> -> this
        is Failure<*> -> throw ResponseFailedException(this)
    }

    @Serializable
    @SerialName("ok")
    data class Success<T>(
        val result: T,
    ) : Response<T>()

    @Serializable
    @SerialName("fail")
    data class Failure<T>(
        val message: String,
        val code: Int,
    ) : Response<T>()

    class PolySerializer<T>(private val serializer: KSerializer<T>) : JsonContentPolymorphicSerializer<Response<*>>(Response::class) {
        override fun selectDeserializer(element: JsonElement) =  when (val stat = (element as? JsonObject)?.get("stat")) {
            JsonPrimitive("ok") -> Success.serializer(serializer)
            JsonPrimitive("fail") -> Failure.serializer(serializer)
            else -> throw SerializationException("stat=$stat not recognized")
        }
    }

    class Serializer<T>(private val serializer: KSerializer<T>) : KSerializer<Response<T>> {

        override val descriptor = buildClassSerialDescriptor(this::class.java.name) {
            element("stat", String.serializer().descriptor)
        }

        override fun deserialize(decoder: Decoder): Response<T> {
            val json = (decoder as? JsonDecoder)?.decodeJsonElement()
                ?: throw SerializationException("only supports JsonDecoder")

            // stupid manual way
            return when (val stat = (json as? JsonObject)?.get("stat")) {
                JsonPrimitive("ok") -> decoder.json.decodeFromJsonElement(serializer,
                    json["result"] ?: throw SerializationException("result required for 'stat'='ok'")
                ).let(::Success)
                JsonPrimitive("fail") -> Failure(
                    json["message"]?.jsonPrimitive?.content
                        ?: throw SerializationException("message required for 'stat'='fail'"),
                    json["code"]?.jsonPrimitive?.content?.toInt(10)
                        ?: throw SerializationException("code required for 'stat'='fail'"),
                )
                else -> throw SerializationException("stat=$stat not recognized")
            }

            // stupid library way
            // i would love to use this kind of approach to instrospect and find the correct
            // serializer. but this is __too__ ugly to even consider using. the next best
            // solution would be to attempt to solve the underlying bug with polymorphic generic
            // serialization.
            // var newDecoder: Decoder? = null
            // try {
            //     decoder.json.decodeFromJsonElement(object : KSerializer<T> {
            //         override val descriptor: SerialDescriptor
            //             get() = this@Serializer.descriptor
            //
            //         override fun deserialize(decoder: Decoder): T {
            //             newDecoder = decoder
            //             throw SerializationException("blah")
            //         }
            //
            //         override fun serialize(encoder: Encoder, value: T) {}
            //     }, json)
            // } catch (e: SerializationException) {}
            //
            // val newNewDecoder = newDecoder ?: throw SerializationException("never received new decoder")
            //
            // return when (val stat = (json as? JsonObject)?.get("stat")) {
            //     JsonPrimitive("ok") -> Success.serializer(serializer).deserialize(newNewDecoder)
            //     JsonPrimitive("fail") -> Failure.serializer(serializer).deserialize(newNewDecoder)
            //     else -> throw SerializationException("stat=$stat not recognized")
            // }
        }

        override fun serialize(encoder: Encoder, value: Response<T>) {
            when (value) {
                is Success<T> -> Success.serializer(serializer).serialize(encoder, value)
                is Failure -> Failure.serializer(serializer).serialize(encoder, value)
            }
        }
    }

}

fun main() {
    PandoraApi().json.apply {
        decodeFromString<Response<Map<String, String>>>("""
{
    "stat": "fail",
    "message": "u suk",
    "code": 9
}
        """).let(::println)

        decodeFromString(Response.serializer(MapSerializer(String.serializer(), String.serializer())), """
{
    "stat": "ok",
    "result": { "a": "b" }
}
        """).let(::println)

        decodeFromString<Response<Map<String, String>>>("""
{
    "stat": "ok",
    "result": { "a": "b" }
}
        """).let(::println)
    }
}
