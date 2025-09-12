package net.thechance.mena.core_chat.data.contacts

import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactCreationRequestDto
import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactDto
import net.thechance.mena.core_chat.data.shared.dto.PagedDataDto
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.exception.ContactsFetchFailedException
import net.thechance.mena.core_chat.domain.model.PagedData
import com.bilalazzam.contacts_provider.Contact as DeviceContact

fun PagedDataDto<ContactDto>?.toPagedListOfContacts(): PagedData<Contact> {
    val pagedData = this ?: throw ContactsFetchFailedException("Response body is null")
    return PagedData(
        data = pagedData.data.orEmpty().toListOfContact(),
        totalItems = pagedData.totalItems ?: 0,
        isLastPage = (pagedData.pageNumber ?: 0) >= (pagedData.totalPages ?: 0)
    )
}

private fun List<ContactDto>.toListOfContact(): List<Contact> {
    return map { it.toDomain() }
}

private fun ContactDto.toDomain(): Contact {
    return Contact(
        name = name.orEmpty(),
        phone = phone.orEmpty(),
        isMenaUser = isMenaUser ?: false,
        imageUrl = imageUrl
    )
}

fun List<Contact>.toListOfContactCreationRequestDto(): List<ContactCreationRequestDto> {
    return map { it.toContactCreationRequestDto() }
}

private fun Contact.toContactCreationRequestDto(): ContactCreationRequestDto {
    return ContactCreationRequestDto(
        name = name,
        phone = phone
    )
}

fun List<DeviceContact>.toListOfContacts(): List<Contact> {
    return flatMap { deviceContact ->
        deviceContact.phoneNumbers.map { phone ->
            Contact(
                name = listOfNotNull(deviceContact.firstName, deviceContact.lastName)
                    .joinToString(" "),
                phone = phone,
                isMenaUser = false,
                imageUrl = null
            )
        }
    }
}

fun List<DeviceContact>.toPagedListOfContacts(): PagedData<Contact> {
    val contacts = this.toListOfContacts()
    return PagedData(
        data = contacts,
        totalItems = contacts.size,
        isLastPage = true
    )
}