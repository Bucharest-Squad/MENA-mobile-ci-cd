package net.thechance.mena.faith.presentation.feature.quran.reciter.downloadedSurahRecitersScreen

import net.thechance.mena.faith.domain.model.Reciter

data class DownloadedSurahRecitersUiState(
    val surahId: Int? = null,
    val allReciters: List<DownloadedSurahRecitersUi> = emptyList(),
    val reciters: List<DownloadedSurahRecitersUi> = emptyList(),
    val query: String = "",
    val queryHint: String = "Search...",
    val selectedReciterId: Int? = null,
)


data class DownloadedSurahRecitersUi(
    val id: Int,
    val name: String,
    val recitingType: String,
    val isDownloaded: Boolean,
)

fun Reciter.toUi(isDownloaded: Boolean) = DownloadedSurahRecitersUi(
    id = id,
    name = name,
    recitingType = tilawahType,
    isDownloaded = isDownloaded
)