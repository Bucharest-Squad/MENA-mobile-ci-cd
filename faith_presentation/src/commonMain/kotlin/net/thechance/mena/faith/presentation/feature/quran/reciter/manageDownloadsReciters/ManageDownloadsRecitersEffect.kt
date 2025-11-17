package net.thechance.mena.faith.presentation.feature.quran.reciter.manageDownloadsReciters

sealed interface ManageDownloadsRecitersEffect {
    data object NavigateBack : ManageDownloadsRecitersEffect
    data object NavigateToSearch: ManageDownloadsRecitersEffect
}
