package net.thechance.mena.core_chat.data.contacts

import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactCreationRequestDto
import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactDto
import net.thechance.mena.core_chat.data.shared.dto.PagedDataDto
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.exception.GetUserContactsException
import net.thechance.mena.core_chat.domain.model.PagedData

fun PagedDataDto<ContactDto>?.toPagedListOfContacts(): PagedData<Contact> {
    val pagedData = this ?: throw GetUserContactsException("Response body is null")
    return PagedData(
        data = pagedData.data.toListOfContact(),
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
