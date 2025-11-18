package net.thechance.mena.faith.presentation.feature.quran.reciter.surahRecitersScreen

import net.thechance.mena.faith.domain.model.Reciter

data class SurahRecitersUiState(
    val surahId: Int? = null,
    val allReciters: List<SurahRecitersUi> = emptyList(),
    val reciters: List<SurahRecitersUi> = emptyList(),
    val query: String = "",
    val queryHint: String = "Search...",
    val selectedReciterId: Int? = null,
)


data class SurahRecitersUi(
    val id: Int,
    val name: String,
    val recitingType: String,
    val isDownloaded: Boolean,
)

fun Reciter.toUi(isDownloaded: Boolean) = SurahRecitersUi(
    id = id,
    name = name,
    recitingType = tilawahType,
    isDownloaded = isDownloaded
)