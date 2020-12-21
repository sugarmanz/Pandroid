package com.jeremiahzucker.pandroid.models

import com.jeremiahzucker.pandroid.models.StringUnionType.Collection
import com.jeremiahzucker.pandroid.models.StringUnionType.Serializer
import com.jeremiahzucker.pandroid.models.StringUnionType.Single
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Utility union type that allows for methods to easily accept
 * a [Single] or a [Collection] of strings. The
 * custom [Serializer] automatically serializes to and from
 * the potential [StringUnionType]s.
 *
 * Represents:
 * string | string[]
 */
@Serializable(with = StringUnionType.Serializer::class)
sealed class StringUnionType {

    @Serializable(with = Single.Serializer::class)
    data class Single(val value: String) : StringUnionType() {

        object Serializer : KSerializer<Single> {
            private val serializer: KSerializer<String> =
                String.serializer()

            override val descriptor = serialDescriptor<String>()

            override fun serialize(encoder: Encoder, value: Single) =
                serializer.serialize(encoder, value.value)

            override fun deserialize(decoder: Decoder) =
                Single(serializer.deserialize(decoder))
        }
    }

    @Serializable(with = Collection.Serializer::class)
    data class Collection(val values: List<String>) : StringUnionType() {

        constructor(vararg values: String) : this(values.toList())

        object Serializer : KSerializer<Collection> {
            private val serializer: KSerializer<List<String>> =
                ListSerializer(String.serializer())

            override val descriptor = serialDescriptor<List<String>>()

            override fun serialize(encoder: Encoder, value: Collection) =
                serializer.serialize(encoder, value.values)

            override fun deserialize(decoder: Decoder) =
                Collection(serializer.deserialize(decoder))
        }
    }

    object Serializer : KSerializer<StringUnionType> {

        /**
         * This is a really problematic [descriptor]. It really
         * should not be a [PrimitiveKind.STRING], rather a [PolymorphicKind.SEALED].
         * However, when using the [PolymorphicKind.SEALED] kind,
         * assumptions are made regarding the underlying structure.
         * Since the [Single] class is serialized as a primitive,
         * those assumptions cause the serialization attempt to
         * blow up. By using [PrimitiveKind], the output it wrapped
         * in a [kotlinx.serialization.json.internal.JsonPrimitiveOutput],
         * which surprisingly uses a [kotlinx.serialization.json.JsonElement]
         * as the backing data type. This allows any form of Json
         * to be returned even though, the [descriptor] says it
         * is a [PrimitiveKind].
         */
        override val descriptor = serialDescriptor<String>()

        override fun serialize(encoder: Encoder, value: StringUnionType) = when (value) {
            is Single -> Single.Serializer.serialize(encoder, value)
            is Collection -> Collection.Serializer.serialize(encoder, value)
        }

        override fun deserialize(decoder: Decoder) = try {
            Single(decoder.decodeString())
        } catch (e: Exception) {
            Collection.Serializer.deserialize(decoder)
        }
    }
}