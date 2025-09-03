package com.inktomi.ace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.inktomi.ace.model.AceChecklist
import com.inktomi.ace.model.Aircraft
import com.inktomi.ace.model.Checklist
import com.inktomi.ace.model.ChecklistGroup
import com.inktomi.ace.model.ChecklistItem
import com.inktomi.ace.repository.InMemoryChecklistRepository
import com.inktomi.ace.ui.theme.ACEChecklistEditorTheme
import com.inktomi.ace.vm.ChecklistViewModel
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistEditor(
    viewModel: ChecklistViewModel,
    modifier: Modifier = Modifier
) {
    val checklists by viewModel.checklists.collectAsState()
    val title = checklists.firstOrNull()?.aircraft?.name ?: "Checklist"

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text(title) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                checklists.forEach { aceChecklist ->
                    aceChecklist.groups.forEach { group ->
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
                            aircraft = Aircraft("New Aircraft"),
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
}

@Preview
@Composable
fun ChecklistEditorPreview() {
    val checklist = AceChecklist(
        aircraft = Aircraft("Cessna 172", "N12345"),
        groups = persistentListOf(
            ChecklistGroup(
                title = "Pre-Flight Inspection",
                checklists = persistentListOf(
                    Checklist(
                        name = "Cockpit",
                        items = persistentListOf(
                            ChecklistItem("Parking Brake", "SET"),
                            ChecklistItem("Ignition", "OFF"),
                            ChecklistItem("Master Switch", "ON"),
                            ChecklistItem("Fuel Quantity", "CHECK"),
                            ChecklistItem("Master Switch", "OFF")
                        )
                    )
                )
            )
        )
    )
    val repository = InMemoryChecklistRepository(listOf(checklist))
    val viewModel = ChecklistViewModel(repository)
    ACEChecklistEditorTheme {
        ChecklistEditor(viewModel)
    }
}
