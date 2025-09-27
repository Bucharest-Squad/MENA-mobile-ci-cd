package net.thechance.mena.identity.data.mapper

import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto
import net.thechance.mena.identity.domain.model.UserInfo


fun ProfileResponseDto.toDomain(): UserInfo{

    return UserInfo(
        firstName = this.firstName,
        lastName  = this.lastName,
        profileImageUrl  = this.profileImageUrl,
        username  = this.username
    )

}
fun UserInfo.toDto(): ProfileResponseDto{

    return ProfileResponseDto(
        firstName = this.firstName,
        lastName  = this.lastName,
        profileImageUrl  = this.profileImageUrl,
        username  = this.username
    )
}