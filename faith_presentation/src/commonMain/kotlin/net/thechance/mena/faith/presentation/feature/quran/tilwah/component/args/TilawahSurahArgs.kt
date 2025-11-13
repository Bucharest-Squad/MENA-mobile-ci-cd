package net.thechance.mena.faith.presentation.feature.quran.tilwah.component.args

interface TilawahSurahArgs {
    val surahId: Int?
        get() = null

    val isSwipeToDeleteEnabled: Boolean
        get() = false

    val isDownloadButtonShown: Boolean
        get() = true
}
