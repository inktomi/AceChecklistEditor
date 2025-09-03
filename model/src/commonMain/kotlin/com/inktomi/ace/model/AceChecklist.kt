package com.inktomi.ace.model

import kotlinx.collections.immutable.ImmutableList

data class AceChecklist(
    val groups: ImmutableList<ChecklistGroup>
)

data class ChecklistGroup(
    val title: String,
    val checklists: ImmutableList<Checklist>
)

data class Checklist(
    val name: String,
    val items: ImmutableList<ChecklistItem>
)

data class ChecklistItem(
    val challenge: String,
    val response: String? = null
)
