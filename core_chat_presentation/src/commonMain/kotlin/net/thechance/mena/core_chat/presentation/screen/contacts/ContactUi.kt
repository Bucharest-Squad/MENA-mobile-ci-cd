package net.thechance.mena.core_chat.presentation.screen.contacts

import net.thechance.mena.core_chat.domain.entity.Contact


data class ContactUi(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val isMenaUser: Boolean,
    val imageUri: String? = null
) {
    val displayName: String
        get() = when {
            firstName.isNotBlank() && lastName.isNotBlank() -> "$firstName $lastName"
            firstName.isNotBlank() -> firstName
            lastName.isNotBlank() -> lastName
            else -> "Unknown"
        }
}
fun Contact.toUiModel(): ContactUi {
    return ContactUi(
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phone,
        isMenaUser = this.isMenaUser,
        imageUri = this.imageUrl
    )
}