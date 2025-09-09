package net.thechance.mena.core_chat.data.contacts

import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactCreationRequestDto
import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactDto
import net.thechance.mena.core_chat.domain.entity.Contact

fun List<ContactDto>.toListOfContact(): List<Contact> {
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

fun List<Contact>.toListOfContactToAddDto(): List<ContactCreationRequestDto> {
    return map { it.toContactToAddDto() }
}

private fun Contact.toContactToAddDto(): ContactCreationRequestDto {
    return ContactCreationRequestDto(
        name = name,
        phone = phone
    )
}