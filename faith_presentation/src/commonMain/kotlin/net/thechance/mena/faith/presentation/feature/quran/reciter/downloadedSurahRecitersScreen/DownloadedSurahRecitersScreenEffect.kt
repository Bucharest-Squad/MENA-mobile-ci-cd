package net.thechance.mena.faith.presentation.feature.quran.reciter.downloadedSurahRecitersScreen

sealed interface DownloadedSurahRecitersScreenEffect {
    data object NavigateBack : DownloadedSurahRecitersScreenEffect
    data object NavigateToSearch: DownloadedSurahRecitersScreenEffect
}
