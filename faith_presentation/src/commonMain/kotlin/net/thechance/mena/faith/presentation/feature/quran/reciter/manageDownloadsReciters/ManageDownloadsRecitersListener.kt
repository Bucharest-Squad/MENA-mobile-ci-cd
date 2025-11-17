package net.thechance.mena.faith.presentation.feature.quran.reciter.manageDownloadsReciters

interface ManageDownloadsRecitersListener {
    fun onBackClick()
    fun onSearchClick()
    fun onQueryChange(query: String)
    fun onClearQueryClick()
    fun onSelectReciterClick(reciterId: Int)
}
