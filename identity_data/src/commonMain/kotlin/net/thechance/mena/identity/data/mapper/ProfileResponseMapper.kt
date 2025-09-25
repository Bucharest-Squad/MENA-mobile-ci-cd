package net.thechance.mena.identity.data.mapper

import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto
import net.thechance.mena.identity.domain.model.UserInfo


fun ProfileResponseDto.toUserInfo(): UserInfo{

    return UserInfo(
        firstName = this.firstName,
        lastName  = this.lastName,
        profileImageUrl  = this.profileImageUrl,
        username  = this.username
    )

}