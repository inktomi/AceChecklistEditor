package com.inktomi.ace.vm

import com.inktomi.ace.model.AceChecklist
import com.inktomi.ace.repository.ChecklistRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class ChecklistViewModel(
    private val repository: ChecklistRepository
) : ViewModel() {

    val checklists: StateFlow<List<AceChecklist>> = repository.getChecklists()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addChecklist(checklist: AceChecklist) {
        viewModelScope.launch {
            repository.saveChecklist(checklist)
        }
    }
}
