package net.thechance.mena.core_chat.domain.entity

data class Contact(
    val name: String,
    val phone: String,
    val isMenaUser: Boolean,
    val imageUrl: String?,
)