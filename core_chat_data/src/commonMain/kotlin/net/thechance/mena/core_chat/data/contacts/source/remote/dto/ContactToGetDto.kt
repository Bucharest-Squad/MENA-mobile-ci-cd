package net.thechance.mena.core_chat.data.contacts.source.remote.dto

data class ContactToGetDto (
    val name: String,
    val phone: String,
    val isMenaUser: Boolean,
    val imageUrl: String?,
)