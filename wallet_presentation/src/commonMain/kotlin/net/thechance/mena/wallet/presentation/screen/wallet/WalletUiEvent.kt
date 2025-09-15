package net.thechance.mena.wallet.presentation.screen.wallet

sealed interface WalletUiEvent {
    data object NavigateBack : WalletUiEvent
}