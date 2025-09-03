package com.inktomi.ace.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.json.Json
import kotlin.test.Test

class SerializationTest {

    private val json = Json { prettyPrint = true }

    @Test
    fun `serializes AceChecklist and excludes aircraft`() {
        val checklist = AceChecklist(
            aircraft = Aircraft("Cessna 172", "N12345"),
            groups = persistentListOf(
                ChecklistGroup(
                    title = "Pre-Flight Inspection",
                    checklists = persistentListOf(
                        Checklist(
                            name = "Cockpit",
                            items = persistentListOf(
                                ChecklistItem("Parking Brake", "SET")
                            )
                        )
                    )
                )
            )
        )

        val serialized = json.encodeToString(AceChecklist.serializer(), checklist)
        val deserialized = json.decodeFromString(AceChecklist.serializer(), serialized)

        assertThat(deserialized.aircraft.name).isEqualTo("Default")
        assertThat(serialized).isEqualTo(
            """
            {
                "groups": [
                    {
                        "title": "Pre-Flight Inspection",
                        "checklists": [
                            {
                                "name": "Cockpit",
                                "items": [
                                    {
                                        "challenge": "Parking Brake",
                                        "response": "SET"
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
            """.trimIndent()
        )
    }

    @Test
    fun `deserializes AceChecklist with default aircraft when aircraft is missing`() {
        val serialized = """
        {
            "groups": [
                {
                    "title": "Pre-Flight Inspection",
                    "checklists": [
                        {
                            "name": "Cockpit",
                            "items": [
                                {
                                    "challenge": "Parking Brake",
                                    "response": "SET"
                                }
                            ]
                        }
                    ]
                }
            ]
        }
        """.trimIndent()

        val deserialized = json.decodeFromString(AceChecklist.serializer(), serialized)

        assertThat(deserialized.aircraft).isNotNull()
        assertThat(deserialized.aircraft.name).isEqualTo("Default")
    }
}
