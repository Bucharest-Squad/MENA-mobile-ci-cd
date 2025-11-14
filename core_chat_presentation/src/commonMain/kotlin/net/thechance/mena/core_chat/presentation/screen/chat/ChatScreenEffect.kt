package net.thechance.mena.core_chat.presentation.screen.chat

import net.thechance.mena.core_chat.presentation.components.snackBarHost.SnackBarData

sealed interface ChatScreenEffect {
    object NavigateBack : ChatScreenEffect
    data class ShowSnackBar(val snackBarData: SnackBarData) : ChatScreenEffect
    object ScrollToBottom: ChatScreenEffect
    data class NavigateToConfirmPayment(val amount:String): ChatScreenEffect

}