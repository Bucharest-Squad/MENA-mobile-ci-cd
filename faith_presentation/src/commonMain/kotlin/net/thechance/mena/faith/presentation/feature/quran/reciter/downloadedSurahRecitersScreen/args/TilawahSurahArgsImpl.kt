package net.thechance.mena.faith.presentation.feature.quran.reciter.downloadedSurahRecitersScreen.args

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import net.thechance.mena.faith.presentation.navigation.Route

class TilawahSurahArgsImpl(
    savedStateHandle: SavedStateHandle
) : TilawahSurahArgs {
    private val downloadedSurah = savedStateHandle.toRoute<Route.DownloadedSurahRecitersRoute>()
    override val surahId: Int? = downloadedSurah.surahId
}
