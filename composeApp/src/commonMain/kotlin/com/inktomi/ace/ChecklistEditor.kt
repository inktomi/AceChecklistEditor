package com.inktomi.ace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.inktomi.ace.model.AceChecklist
import com.inktomi.ace.model.Checklist
import com.inktomi.ace.model.ChecklistGroup
import com.inktomi.ace.model.ChecklistItem
import com.inktomi.ace.repository.InMemoryChecklistRepository
import com.inktomi.ace.vm.ChecklistViewModel
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChecklistEditor(
    viewModel: ChecklistViewModel
) {
    val checklists by viewModel.checklists.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            checklists.forEach { aceChecklist ->
                aceChecklist.groups.forEach { group ->
                    item {
                        Text(
                            text = group.title,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    group.checklists.forEach { checklist ->
                        item {
                            Text(
                                text = checklist.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 4.dp)
                            )
                        }
                        items(checklist.items) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(item.challenge)
                                Text(item.response ?: "")
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                viewModel.addChecklist(
                    AceChecklist(
                        groups = persistentListOf(
                            ChecklistGroup(
                                title = "New Checklist Group",
                                checklists = persistentListOf(
                                    Checklist(
                                        name = "New Checklist",
                                        items = persistentListOf(
                                            ChecklistItem(
                                                challenge = "New Challenge",
                                                response = "New Response"
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Add Checklist")
        }
    }
}

@Preview
@Composable
fun ChecklistEditorPreview() {
    val checklist = AceChecklist(
        groups = persistentListOf(
            ChecklistGroup(
                title = "Cessna 172",
                checklists = persistentListOf(
                    Checklist(
                        name = "Pre-Flight Inspection",
                        items = persistentListOf(
                            ChecklistItem("Cockpit", "CHECK"),
                            ChecklistItem("Fuselage", "CHECK"),
                            ChecklistItem("Empennage", "CHECK"),
                            ChecklistItem("Right Wing", "CHECK"),
                            ChecklistItem("Nose", "CHECK"),
                            ChecklistItem("Left Wing", "CHECK")
                        )
                    ),
                    Checklist(
                        name = "Before Engine Start",
                        items = persistentListOf(
                            ChecklistItem("Pre-flight inspection", "COMPLETE"),
                            ChecklistItem("Passenger briefing", "COMPLETE"),
                            ChecklistItem("Seats, belts, rudders", "ADJUST & LOCK"),
                            ChecklistItem("Avionics", "OFF"),
                            ChecklistItem("Brakes", "TEST & SET"),
                            ChecklistItem("Fuel selector", "BOTH"),
                            ChecklistItem("Fuel shutoff valve", "ON"),
                            ChecklistItem("Circuit breakers", "CHECK IN")
                        )
                    )
                )
            )
        )
    )
    val repository = InMemoryChecklistRepository(listOf(checklist))
    val viewModel = ChecklistViewModel(repository)
    ChecklistEditor(viewModel)
}
