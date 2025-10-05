package net.thechance.mena.identity.data.mapper

import net.thechance.mena.identity.data.dataSource.local.database.model.UserEntity
import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto
import net.thechance.mena.identity.domain.entity.User


fun ProfileResponseDto.toDomain(): User {
    return User(
        firstName = this.firstName,
        lastName = this.lastName,
        profileImageUrl = this.profileImageUrl,
        username = this.username
    )
}

fun ProfileResponseDto.toEntity(): UserEntity {
    return UserEntity(
        firstName = this.firstName,
        lastName = this.lastName,
        profileImageUrl = this.profileImageUrl,
        username = this.username
    )
}

fun UserEntity.toDomain(): User {

    return User(
        firstName = this.firstName,
        lastName = this.lastName,
        profileImageUrl = this.profileImageUrl,
        username = this.username
    )

}

fun User.toEntity(): UserEntity {

    return UserEntity(
        firstName = this.firstName,
        lastName = this.lastName,
        profileImageUrl = this.profileImageUrl,
        username = this.username
    )
}