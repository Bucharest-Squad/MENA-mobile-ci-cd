package net.thechance.mena.core_chat.presentation.screen.syncContacts

data class SyncContactsUiState(
    val isLoading: Boolean = false,
    val isSyncFinished: Boolean = false,
    val error: String? = null
)