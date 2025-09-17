package net.thechance.mena.core_chat.presentation.screen.contacts

import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.exception.ChatException
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import net.thechance.mena.core_chat.presentation.components.SnackBarData
import net.thechance.mena.core_chat.presentation.navigation.ChatDetailsRoute
import net.thechance.mena.core_chat.presentation.navigation.SyncContactsRoute
import net.thechance.mena.core_chat.presentation.shared.BasePagingSource
import net.thechance.mena.core_chat.presentation.shared.BaseViewModel

class ContactsViewModel(
    private val contactsRepository: ContactsRepository,
) : BaseViewModel<ContactsScreenState>(ContactsScreenState()),
    ContactsScreenInteractionListener {

    init {
        loadContacts()
    }

    private fun loadContacts() {
        tryToCollect(
            collect = ::loadContactsOperation,
            onCollect = ::onLoadContactsSuccess,
            onError = ::onDataLoadError
        )
    }

    private fun loadContactsOperation(): Flow<PagingData<ContactUi>> {
        return createPagingFlow(
            pagingSourceFactory = { createContactsPagingSource() },
            mapper = Contact::toUiModel
        )
    }

    private fun onLoadContactsSuccess(pagingData: PagingData<ContactUi>?) {
        updateState { it.copy(contacts = flowOf(pagingData ?: PagingData.empty())) }
    }

    override fun onRefreshContacts() {
        loadContacts()
    }

    override fun onBackClick() {
        popBackStack()
    }

    override fun onResyncClick() {
        navigate(SyncContactsRoute(forceSync = true))
    }

    override fun onContactClick(contactId: Int) {
        navigate(ChatDetailsRoute(contactId = contactId))
    }

    private fun onDataLoadError(e: Throwable) {
        showSnackBar(
            SnackBarData(
                title = "Something went wrong",
                message = e.message ?: "Unknown error",
            )
        )
    }

    private fun createContactsPagingSource(onError: ((ChatException) -> Unit)? = ::onDataLoadError)
            : PagingSource<Int, Contact> {
        return BasePagingSource(
            onError = onError,
            fetchItems = { page, pageSize ->
                contactsRepository.getUserContacts(page, pageSize)
            }
        )
    }
}