package net.thechance.mena.core_chat.presentation.navigation

import kotlinx.serialization.Serializable

interface ChatRoute

@Serializable
data object MainRoute : ChatRoute

@Serializable
data object ContactsRoute : ChatRoute