package net.thechance.mena.core_chat.presentation.screen.syncContacts

data class SyncContactsUiState(
    val isLoading: Boolean = false,
    val showSyncView: Boolean = false,
    val isFirstSync: Boolean = false,
    val error: String? = null
)