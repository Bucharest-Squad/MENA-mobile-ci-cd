package net.thechance.mena.faith.presentation.feature.quran.reciter.downloadedReciters

import net.thechance.mena.faith.domain.model.Reciter

data class DownloadedRecitersUiState(
    val surahId: Int? = null,
    val allReciters: List<DownloadedRecitersUi> = emptyList(),
    val reciters: List<DownloadedRecitersUi> = emptyList(),
    val query: String = "",
    val queryHint: String = "Search...",
    val selectedReciterId: Int? = null,
    val isSwipeable: Boolean = false,
)

data class DownloadedRecitersUi(
    val id: Int,
    val name: String,
    val recitingType: String,
    val isDownloaded: Boolean,
)

fun Reciter.toUi(isDownloaded: Boolean) = DownloadedRecitersUi(
    id = id,
    name = name,
    recitingType = tilawahType,
    isDownloaded = isDownloaded
)
