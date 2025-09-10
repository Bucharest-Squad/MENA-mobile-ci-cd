package net.thechance.mena.core_chat.data.contacts.source.remote

import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactCreationRequestDto
import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactDto
import net.thechance.mena.core_chat.data.shared.dto.PagedDataDto
import net.thechance.mena.core_chat.data.shared.dto.BaseResponseDto

class DummyContactsDataSource {
    val fakeContactsList = mutableListOf(
        ContactDto(
            name = "Ahmed",
            phone = "+201234567890",
            isMenaUser = true,
            imageUrl = "https://picsum.photos/200"
        ),
        ContactDto(
            name = "Mohamed",
            phone = "+201098765432",
            isMenaUser = false,
            imageUrl = "https://picsum.photos/200"
        ),
        ContactDto(
            name = "Sara",
            phone = "+201112223334",
            isMenaUser = true,
            imageUrl = "https://picsum.photos/200"
        ),
        ContactDto(
            name = "Laila",
            phone = "+201223344556",
            isMenaUser = false,
            imageUrl = "https://picsum.photos/200"
        ),
        ContactDto(
            name = "Omar",
            phone = "+201334455667",
            isMenaUser = true,
            imageUrl = "https://picsum.photos/200"
        )
    ).apply {
        // Add 95 more fake contacts to make the list size 100
        for (i in 6..100) {
            add(
                ContactDto(
                    name = "User$i",
                    phone = "+201${100000000 + i}",
                    isMenaUser = i % 2 == 0,
                    imageUrl = null
                )
            )
        }
    }

    suspend fun getUserContacts(
        pageNumber: Int,
        pageSize: Int
    ): BaseResponseDto<PagedDataDto<ContactDto>> {
        val fromIndex = (pageNumber - 1) * pageSize
        val toIndex = (fromIndex + pageSize).coerceAtMost(fakeContactsList.size)
        if (fromIndex >= fakeContactsList.size) {
            return BaseResponseDto(
                success = true,
                message = "No contacts found",
                status = 200,
                body = PagedDataDto(
                    data = emptyList(),
                    pageNumber = pageNumber,
                    pageSize = pageSize,
                    totalItems = fakeContactsList.size,
                    totalPages = (fakeContactsList.size + pageSize - 1) / pageSize
                )
            )
        }
        val data = fakeContactsList.subList(fromIndex, toIndex)
        val totalItems = fakeContactsList.size
        val totalPages = (totalItems + pageSize - 1) / pageSize
        return BaseResponseDto(
            success = true,
            message = "Contacts fetched successfully",
            status = 200,
            body = PagedDataDto(
                data = data,
                pageNumber = pageNumber,
                pageSize = pageSize,
                totalItems = totalItems,
                totalPages = totalPages
            )
        )
    }

    suspend fun syncContacts(
        contacts: List<ContactCreationRequestDto>
    ): BaseResponseDto<Unit> {
        fakeContactsList.addAll(
            contacts.map {
                ContactDto(
                    name = it.name,
                    phone = it.phone,
                    isMenaUser = false,
                    imageUrl = null
                )
            }
        )
        return BaseResponseDto(
            success = true,
            message = "Contacts synced successfully",
            status = 200,
        )
    }
}