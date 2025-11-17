package net.thechance.mena.faith.presentation.feature.quran.reciter.downloadedSurahRecitersScreen

interface DownloadedSurahRecitersListener {
    fun onBackClick()
    fun onSearchClick()
    fun onDownloadClick(reciterId: Int)
    fun onSelectReciterClick(reciterId: Int)
    fun onQueryChange(newQuery: String)
    fun onClearQueryClick()
}
