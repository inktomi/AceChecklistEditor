package com.inktomi.ace

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.inktomi.ace.repository.InMemoryChecklistRepository
import com.inktomi.ace.vm.ChecklistViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = remember {
            ChecklistViewModel(
                repository = InMemoryChecklistRepository()
            )
        }
        ChecklistEditor(viewModel = viewModel)
    }
}