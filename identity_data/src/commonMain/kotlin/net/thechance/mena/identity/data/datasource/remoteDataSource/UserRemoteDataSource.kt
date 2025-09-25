package net.thechance.mena.identity.data.datasource.remoteDataSource

import net.thechance.mena.identity.data.dto.auth.LoginRequestDto
import net.thechance.mena.identity.data.dto.auth.LoginResponseDto
import net.thechance.mena.identity.data.dto.auth.RefreshRequestDto
import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto

interface UserRemoteDataSource {
    suspend fun login(loginRequest: LoginRequestDto): LoginResponseDto
    suspend fun refreshToken(refreshRequest: RefreshRequestDto): LoginResponseDto
    suspend fun getUserInfo(): ProfileResponseDto


}