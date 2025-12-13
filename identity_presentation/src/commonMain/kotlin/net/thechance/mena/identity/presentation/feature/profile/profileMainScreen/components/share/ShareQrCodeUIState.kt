package net.thechance.mena.identity.presentation.feature.profile.profileMainScreen.components.share

data class ShareQrCodeUIState(
    val showDialog: Boolean = false,
    val isLoading: Boolean = false,
    val shareLinkUrl: String = "",
)