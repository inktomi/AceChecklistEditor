package com.inktomi.ace.repository

import com.inktomi.ace.model.AceChecklist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryChecklistRepository(
    initialChecklists: List<AceChecklist> = emptyList()
) : ChecklistRepository {
    private val checklists = MutableStateFlow(initialChecklists)

    override fun getChecklists(): Flow<List<AceChecklist>> = checklists.asStateFlow()

    override suspend fun saveChecklist(checklist: AceChecklist) {
        checklists.value = checklists.value + checklist
    }
}
