package net.thechance.mena.core_chat.data.contacts.fakes

import net.thechance.mena.core_chat.data.contacts.dto.ContactDto
import net.thechance.mena.core_chat.data.contacts.toDomain
import net.thechance.mena.core_chat.data.shared.dto.PagedDataDto
import com.bilalazzam.contacts_provider.Contact as DeviceContact


val sampleDeviceContact = DeviceContact(
    id = "1",
    firstName = "Bilal",
    lastName = "Azzam",
    phoneNumbers = listOf("01026388780")
)

val sampleContactDto = ContactDto(
    name = "Bilal Azzam",
    phoneNumber = "01026388780",
    isMenaMember = true,
    imageUrl = "http://example.com/image.jpg"
)

val sampleContact = sampleContactDto.toDomain()

fun createSamplePagedData(
    pageNumber: Int = 1,
    pageSize: Int = 10,
    totalItems: Int = 15,
    totalPages: Int = 2
    ) =
    PagedDataDto(
        data = List(pageSize) { i ->
            ContactDto(
                name = "Page$pageNumber User$i",
                phoneNumber = "010000000$i",
                isMenaMember = false,
                imageUrl = null
            )
        },
        pageNumber = pageNumber,
        pageSize = pageSize,
        totalItems = totalItems,
        totalPages = totalPages
    )
