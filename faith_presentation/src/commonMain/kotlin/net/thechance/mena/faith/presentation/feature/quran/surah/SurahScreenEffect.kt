package net.thechance.mena.faith.presentation.feature.quran.surah

sealed class SurahScreenEffect {
    object NavigateBack : SurahScreenEffect()
    data class ShareAyah(val ayah: String) : SurahScreenEffect()
    object CopyAyahSuccess : SurahScreenEffect()
    object CopyAyahFail : SurahScreenEffect()
}