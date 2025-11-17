package net.thechance.mena.faith.presentation.feature.quran.reciter.manageDownloadsReciters

import net.thechance.mena.faith.domain.model.Reciter

data class ManageDownloadsRecitersUiState(
    val surahId: Int? = null,
    val allReciters: List<ManageDownloadsRecitersUi> = emptyList(),
    val reciters: List<ManageDownloadsRecitersUi> = emptyList(),
    val query: String = "",
    val queryHint: String = "Search...",
    val selectedReciterId: Int? = null,
    val isSwipeable: Boolean = false,
)

data class ManageDownloadsRecitersUi(
    val id: Int,
    val name: String,
    val recitingType: String,
    val isDownloaded: Boolean,
)

fun Reciter.toUi(isDownloaded: Boolean) = ManageDownloadsRecitersUi(
    id = id,
    name = name,
    recitingType = tilawahType,
    isDownloaded = isDownloaded
)
