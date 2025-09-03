package com.inktomi.ace

import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
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

@Composable
@Preview
fun App(modifier: Modifier = Modifier) {
    ACEChecklistEditorTheme {
        Surface(modifier = modifier) {
            val inPreview = LocalInspectionMode.current
            val viewModel = remember {
                val repository = if (inPreview) {
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
                    InMemoryChecklistRepository(listOf(checklist))
                } else {
                    InMemoryChecklistRepository()
                }
                ChecklistViewModel(repository)
            }
            ChecklistEditor(viewModel = viewModel)
        }
    }
}
