package net.thechance.mena.faith.presentation.feature.quran.reciter.reciterSelection

import net.thechance.mena.faith.domain.model.Reciter


data class ReciterSelectionUiState(
    val query: String = "",
    val queryHint: String = "Search reciter",
    val lastSearchedQuery: String = "",
    val searchResults: List<ReciterSelectionUi> = emptyList(),
    val selectedReciterId: Int? = null,
    )

data class ReciterSelectionUi(
    val id: Int,
    val name: String,
    val recitingType: String,
)

fun Reciter.toUi() = ReciterSelectionUi(
    id = id,
    name = name,
    recitingType = tilawahType,
)