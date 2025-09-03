package com.inktomi.ace.repository

import com.inktomi.ace.model.AceChecklist
import kotlinx.coroutines.flow.Flow

interface ChecklistRepository {
    fun getChecklists(): Flow<List<AceChecklist>>
    suspend fun saveChecklist(checklist: AceChecklist)
}
