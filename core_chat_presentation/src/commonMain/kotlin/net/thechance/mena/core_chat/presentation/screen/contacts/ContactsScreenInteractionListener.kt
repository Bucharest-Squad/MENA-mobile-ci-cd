package net.thechance.mena.core_chat.presentation.screen.contacts

interface ContactsScreenInteractionListener : ContactListInteractionListener{
    fun onBackClick()
    fun onResyncClick()
}

interface ContactListInteractionListener{
    fun onContactClick(contactId: Int)
    fun onRefreshContacts()
}