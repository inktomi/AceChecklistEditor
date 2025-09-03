package com.inktomi.ace.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class Aircraft(
    val name: String,
    val registrationId: String? = null
)

@Serializable
data class AceChecklist(
    @Transient val aircraft: Aircraft = Aircraft("Default"),
    @Serializable(with = ImmutableListSerializer::class)
    val groups: ImmutableList<ChecklistGroup>
)

@Serializable
data class ChecklistGroup(
    val title: String,
    @Serializable(with = ImmutableListSerializer::class)
    val checklists: ImmutableList<Checklist>
)

@Serializable
data class Checklist(
    val name: String,
    @Serializable(with = ImmutableListSerializer::class)
    val items: ImmutableList<ChecklistItem>
)

@Serializable
data class ChecklistItem(
    val challenge: String,
    val response: String? = null
)

class ImmutableListSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<ImmutableList<T>> {
    private val listSerializer = kotlinx.serialization.builtins.ListSerializer(dataSerializer)
    override val descriptor: SerialDescriptor = listSerializer.descriptor

    override fun serialize(encoder: Encoder, value: ImmutableList<T>) {
        listSerializer.serialize(encoder, value.toList())
    }

    override fun deserialize(decoder: Decoder): ImmutableList<T> {
        return listSerializer.deserialize(decoder).toImmutableList()
    }
}
