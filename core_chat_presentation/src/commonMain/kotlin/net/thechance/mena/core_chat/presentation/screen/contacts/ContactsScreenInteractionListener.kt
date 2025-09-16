package net.thechance.mena.core_chat.presentation.screen.contacts

interface ContactsScreenInteractionListener : ContactItemInteractionListener{
    fun onBackClick()
    fun onResyncClick()
}
interface ContactItemInteractionListener{
    fun onContactClick(contactId: Int)
}