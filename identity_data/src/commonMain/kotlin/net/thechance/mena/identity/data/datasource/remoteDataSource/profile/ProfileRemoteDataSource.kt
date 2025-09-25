package net.thechance.mena.identity.data.datasource.remoteDataSource.profile

import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto

interface ProfileRemoteDataSource {

    suspend fun getUserInfo(): ProfileResponseDto
}