package net.thechance.mena.faith.presentation.feature.quran.reciter.downloadedSurahRecitersScreen.args

interface TilawahSurahArgs {
    val surahId: Int?

    val isSwipeToDeleteEnabled: Boolean
        get() = false
}
