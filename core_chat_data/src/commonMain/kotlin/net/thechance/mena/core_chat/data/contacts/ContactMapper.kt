package net.thechance.mena.core_chat.data.contacts

import net.thechance.mena.core_chat.data.contacts.source.remote.BaseResponseDto
import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactCreationRequestDto
import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactDto
import net.thechance.mena.core_chat.data.contacts.source.remote.dto.PagedDataDto
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.exception.ContactsException
import net.thechance.mena.core_chat.domain.model.PagedData

fun BaseResponseDto<PagedDataDto<ContactDto>>.toPagedListOfContacts(): PagedData<Contact> {
    if (!success) {
        throw ContactsException(message ?: "Couldn't get user contacts")
    }
    val body = this.body ?: throw ContactsException("Response body is null")
    val pagedData = body
    return PagedData(
        data = pagedData.data.toListOfContact(),
        totalItems = pagedData.totalItems,
        isLastPage = pagedData.pageNumber >= pagedData.totalPages
    )
}


fun BaseResponseDto<Unit>.successOrThrow(message: String) {
    if (!success) {
        throw ContactsException(this.message ?: message)
    }
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
