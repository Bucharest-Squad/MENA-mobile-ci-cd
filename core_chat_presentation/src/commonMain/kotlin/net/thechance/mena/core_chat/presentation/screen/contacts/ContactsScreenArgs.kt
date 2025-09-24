package net.thechance.mena.core_chat.presentation.screen.contacts

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ContactsScreenArgs {

    fun getSyncSuccessState() : StateFlow<Boolean>
    fun setIsSyncSuccessToFalse()

}

class ContactsScreenArgsImpl(private val _isSyncSuccess: MutableStateFlow<Boolean>) : ContactsScreenArgs {

    override fun getSyncSuccessState () = _isSyncSuccess.asStateFlow()

    override fun setIsSyncSuccessToFalse(){
        _isSyncSuccess.value = false
    }

    companion object {
        const val IS_SYNC_SUCCESS = "is_sync_success"
    }
}