package com.jeremiahzucker.pandroid.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

@Serializable
sealed class PolymorphicResponse {

    @Serializable
    @SerialName("ok")
    data class PolymorphicSuccess(
        val result: JsonElement,
    ) : PolymorphicResponse()

    @Serializable
    @SerialName("fail")
    data class PolymorphicFailure(
        val code: Int,
        val message: String,
    ) : PolymorphicResponse()

}

@Serializable
sealed class GenericPolymorphicResponse<out T> {

    @Serializable
    @SerialName("ok")
    data class GenericPolymorphicSuccess<out T>(
        val result: T,
    ) : GenericPolymorphicResponse<T>()

    @Serializable
    @SerialName("fail")
    data class GenericPolymorphicFailure(
        val code: Int,
        val message: String,
    ) : GenericPolymorphicResponse<Unit>()

}

@Serializable(with = GenericJsonContentPolymorphicResponse.Serializer::class)
sealed class GenericJsonContentPolymorphicResponse<out T> {

    @Serializable
    @SerialName("ok")
    data class GenericJsonContentPolymorphicSuccess<out T>(
        val stat: String,
        val result: T,
    ) : GenericJsonContentPolymorphicResponse<T>()

    @Serializable
    @SerialName("fail")
    data class GenericJsonContentPolymorphicFailure(
        val stat: String,
        val code: Int,
        val message: String,
    ) : GenericJsonContentPolymorphicResponse<Unit>()

    class Serializer<T>(private val serializer: KSerializer<T>) : JsonContentPolymorphicSerializer<GenericJsonContentPolymorphicResponse<*>>(GenericJsonContentPolymorphicResponse::class) {
        override fun selectDeserializer(element: JsonElement) = when (val stat = (element as? JsonObject)?.get("stat")) {
            JsonPrimitive("ok") -> GenericJsonContentPolymorphicSuccess.serializer(serializer)
            JsonPrimitive("fail") -> GenericJsonContentPolymorphicFailure.serializer()
            else -> throw SerializationException("class discriminator stat=$stat not recognized")
        }
    }

}

@Serializable
data class Result(
    val message: String,
)

val json = Json {
    classDiscriminator = "stat"
}

inline fun <reified T0, reified T1, reified T2> testResponseSerializer() = with (json) {
    decodeFromString<T0>("""
            {
                "stat": "fail",
                "code": 9999,
                "message": "some failure message"
            }
        """).let(::println)

    decodeFromString<T2>(
        """
            {
                "stat": "ok",
                "result": {
                    "message": "complex success"
                }
            }
        """
    ).let(::println)

    decodeFromString<T1>("""
            {
                "stat": "ok",
                "result": "we succeeded"
            }
        """).let(::println)

}

fun main() {

    testResponseSerializer<
        PolymorphicResponse,
        PolymorphicResponse,
        PolymorphicResponse>()

    testResponseSerializer<
        GenericJsonContentPolymorphicResponse<String>,
        GenericJsonContentPolymorphicResponse<String>,
        GenericJsonContentPolymorphicResponse<Result>>()

    testResponseSerializer<
        GenericPolymorphicResponse<String>,
        GenericPolymorphicResponse<String>,
        GenericPolymorphicResponse<Result>>()

}