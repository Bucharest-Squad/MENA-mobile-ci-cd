package net.thechance.mena.faith.presentation.feature.quran.tilwah

interface TilawahInteractionListener {
    fun onBackClick()
    fun onSearchClick()

    fun onDownloadClick(surahId: Int, reciterId: Int)

    fun onSelectReciterClick(reciterId: Int)
}