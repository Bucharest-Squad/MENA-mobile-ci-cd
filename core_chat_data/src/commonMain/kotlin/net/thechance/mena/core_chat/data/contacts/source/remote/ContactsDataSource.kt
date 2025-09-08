package net.thechance.mena.core_chat.data.contacts.source.remote

import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactToAddDto
import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactToGetDto

class ContactsRemoteDataSource {
    val fakeContactsList = mutableListOf(
        ContactToGetDto(
            name = "Ahmed",
            phone = "+201234567890",
            isMenaUser = true,
            imageUrl = "https://picsum.photos/200"
        ),
        ContactToGetDto(
            name = "Mohamed",
            phone = "+201098765432",
            isMenaUser = false,
            imageUrl = "https://picsum.photos/200"
        ),
        ContactToGetDto(
            name = "Sara",
            phone = "+201112223334",
            isMenaUser = true,
            imageUrl = "https://picsum.photos/200"
        ),
        ContactToGetDto(
            name = "Laila",
            phone = "+201223344556",
            isMenaUser = false,
            imageUrl = "https://picsum.photos/200"
        ),
        ContactToGetDto(
            name = "Omar",
            phone = "+201334455667",
            isMenaUser = true,
            imageUrl = "https://picsum.photos/200"
        )
    ).apply {
        // Add 95 more fake contacts to make the list size 100
        for (i in 6..100) {
            add(
                ContactToGetDto(
                    name = "User$i",
                    phone = "+201${100000000 + i}",
                    isMenaUser = i % 2 == 0,
                    imageUrl = null
                )
            )
        }
    }

    suspend fun getUserContacts(
        userId: String,
        pageNumber: Int,
        pageSize: Int
    ): List<ContactToGetDto> {
        val fromIndex = (pageNumber - 1) * pageSize
        val toIndex = (fromIndex + pageSize).coerceAtMost(fakeContactsList.size)
        if (fromIndex >= fakeContactsList.size) return emptyList()
        return fakeContactsList.subList(fromIndex, toIndex)
    }

    suspend fun syncContacts(
        userId: String,
        contacts: List<ContactToAddDto>
    ): List<ContactToGetDto> {
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