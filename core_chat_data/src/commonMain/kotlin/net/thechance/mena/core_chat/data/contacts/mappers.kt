package net.thechance.mena.core_chat.data.contacts

import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactToAddDto
import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactToGetDto
import net.thechance.mena.core_chat.domain.entity.Contact

fun List<ContactToGetDto>.mapToDomain(): List<Contact> {
    return map { it.toDomain() }
}

private fun ContactToGetDto.toDomain(): Contact {
    return Contact(
        name = name,
        phone = phone,
        isMenaUser = isMenaUser,
        imageUrl = imageUrl
    )
}

fun List<Contact>.toContactToAddDto(): List<ContactToAddDto> {
    return map { it.toContactToAddDto() }
}

private fun Contact.toContactToAddDto(): ContactToAddDto {
    return ContactToAddDto(
        name = name,
        phone = phone
    )
}