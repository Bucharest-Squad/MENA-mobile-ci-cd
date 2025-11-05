package net.thechance.mena.identity.data.mapper

import net.thechance.mena.identity.data.dataSource.local.database.model.UserEntity
import net.thechance.mena.identity.data.dto.auth.RegisterRequestDto
import net.thechance.mena.identity.domain.entity.Gender
import net.thechance.mena.identity.domain.model.RegisterRequest

fun RegisterRequest.toDto(sessionId: String): RegisterRequestDto {
    return RegisterRequestDto(
        phoneNumber = phoneNumber.getFormattedPhoneNumber(),
        username = username,
        firstName = firstName,
        lastName = lastName,
        birthDate = birthDate.toString(),
        gender = gender.toInt(),
        password = password,
        sessionId = sessionId
    )
}

private fun Gender.toInt(): Int {
    return when (this) {
        Gender.MALE -> UserEntity.MALE
        Gender.FEMALE -> UserEntity.FEMALE
    }
}