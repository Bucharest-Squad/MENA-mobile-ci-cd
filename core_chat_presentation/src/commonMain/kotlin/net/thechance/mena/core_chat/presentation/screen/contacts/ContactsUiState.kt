package net.thechance.mena.core_chat.presentation.screen.contacts

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ContactsUiState(
    val contacts: Flow<PagingData<ContactUiModel>> = emptyFlow(),
    val isLastPage: Boolean = false,
    val error: String? = null
)   
