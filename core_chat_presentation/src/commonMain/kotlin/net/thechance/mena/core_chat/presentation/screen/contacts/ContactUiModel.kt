package net.thechance.mena.core_chat.presentation.screen.contacts

import net.thechance.mena.core_chat.domain.entity.Contact


data class ContactUiModel(
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String,
    val isMenaUser: Boolean,
    val imageUri: String? = null
) {
    val displayName: String
        get() = when {
            !firstName.isNullOrBlank() && !lastName.isNullOrBlank() -> "$firstName $lastName"
            !firstName.isNullOrBlank() -> firstName
            !lastName.isNullOrBlank() -> lastName
            else -> "Unknown"
        }

    val initials: String
        get() = when {
            !firstName.isNullOrBlank() && !lastName.isNullOrBlank() ->
                "${firstName.firstOrNull()} ${lastName.firstOrNull()}".uppercase()
            !firstName.isNullOrBlank() ->
                firstName.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
            !lastName.isNullOrBlank() ->
                lastName.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
            else -> "?"
        }
}
fun Contact.toUiModel(): ContactUiModel {
    return ContactUiModel(
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phone,
        isMenaUser = this.isMenaUser,
        imageUri = this.imageUrl
    )
}
val temporaryContacts = listOf(
    ContactUiModel(
        firstName = "John",
        lastName = "Doe",
        phoneNumber = "+1234567890",
        isMenaUser = true,
        imageUri = "https://fastly.picsum.photos/id/834/200/200.jpg?hmac=vcoSQ7O6i2vxWANscm-9EGrw0MNqLzU3X0pQZ1o5ovI"
    ),
    ContactUiModel(
        firstName = "Noor",
        lastName = "Al-Taie",
        phoneNumber = "+1987654321",
        isMenaUser = false,
        imageUri = null
    ),
    ContactUiModel(
        firstName = "Alice",
        lastName = "Johnson",
        phoneNumber = "+1122334455",
        isMenaUser = true,
        imageUri = "https://fastly.picsum.photos/id/310/200/200.jpg?hmac=gpEKQ-zUG9L-jZga6K0jQ2NHHqqoPzMKUR-_ZmiL734"
    ),
    ContactUiModel(
        firstName = "Bob",
        lastName = "Brown",
        phoneNumber = "+1222333444",
        isMenaUser = false,
        imageUri = null
    ),
    ContactUiModel(
        firstName = null,
        lastName = null,
        phoneNumber = "+1555666777",
        isMenaUser = false,
        imageUri = "https://fastly.picsum.photos/id/57/200/200.jpg?hmac=EAluVy04ceTUijEPw3vraS5dkJ6vtBD3HmNwvMI5f3k"
    )
)