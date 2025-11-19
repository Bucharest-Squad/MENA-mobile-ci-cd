package net.thechance.mena.faith.presentation.feature.quran.reciter.reciterSelection

sealed interface RecitersSelectionEffect {
    data object NavigateBack : RecitersSelectionEffect
}