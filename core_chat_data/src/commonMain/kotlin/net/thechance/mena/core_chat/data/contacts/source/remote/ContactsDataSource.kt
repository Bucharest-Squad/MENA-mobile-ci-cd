package net.thechance.mena.core_chat.data.contacts.source.remote

import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactToAddDto
import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactToGetDto

class ContactsRemoteDataSource {
    val fakeContactsList = mutableListOf(
        ContactToGetDto(
            name = "Ahmed",
            phone = "+201234567890",
            isMenaUser = true,
            imageUrl = null
        ),
        ContactToGetDto(
            name = "Mohamed",
            phone = "+201098765432",
            isMenaUser = false,
            imageUrl = null
        ),
        ContactToGetDto(
            name = "Sara",
            phone = "+201112223334",
            isMenaUser = true,
            imageUrl = null
        ),
        ContactToGetDto(
            name = "Laila",
            phone = "+201223344556",
            isMenaUser = false,
            imageUrl = null
        ),
        ContactToGetDto(
            name = "Omar",
            phone = "+201334455667",
            isMenaUser = true,
            imageUrl = null
        )
    )

    suspend fun getUserContacts(userId: String): List<ContactToGetDto> {
        return fakeContactsList
    }

    suspend fun syncContacts(userId: String, contacts: List<ContactToAddDto>): List<ContactToGetDto> {
        fakeContactsList.addAll(
            contacts.map {
                ContactToGetDto(
                    name = it.name,
                    phone = it.phone,
                    isMenaUser = false,
                    imageUrl = null
                )
            }
        )
        return fakeContactsList.toList()
    }
}