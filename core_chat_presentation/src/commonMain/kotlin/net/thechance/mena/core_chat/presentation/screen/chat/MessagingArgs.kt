package net.thechance.mena.core_chat.presentation.screen.messaging

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import net.thechance.mena.core_chat.presentation.navigation.MessagingRoute

interface MessagingArgs {

    val chatId: String
}

class MessagingArgsImpl(savedStateHandle: SavedStateHandle) : MessagingArgs {

    override val chatId: String = savedStateHandle.toRoute<MessagingRoute>().chatId
}