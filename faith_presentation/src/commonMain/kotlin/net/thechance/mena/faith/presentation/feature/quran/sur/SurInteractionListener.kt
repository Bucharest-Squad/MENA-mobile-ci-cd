package net.thechance.mena.faith.presentation.feature.quran.sur

interface SurInteractionListener {
    fun onClickSurah(surahId: Int, surahName: String)
    fun onClickBack()
    fun onClickBookmark()
    fun onClickSearch()
}
