package net.thechance.mena.core_chat.presentation.screen.contacts

interface ContactsScreenInteractionListener{
    fun onBackClick()
    fun onResyncClick()
    fun onContactClick(contactId: Int)
}