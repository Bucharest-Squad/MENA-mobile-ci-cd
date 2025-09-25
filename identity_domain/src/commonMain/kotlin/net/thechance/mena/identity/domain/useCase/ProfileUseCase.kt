package net.thechance.mena.identity.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.thechance.mena.identity.domain.model.UserInfo
import net.thechance.mena.identity.domain.repository.ProfileRepository

class ProfileUseCase(
    private val profileRepository: ProfileRepository

) {
    suspend fun getUserInfo(): Flow<UserInfo> = flow {

        val localUserInfo = profileRepository.getUserInfoFromLocal()
        if (localUserInfo != null) {
            emit(localUserInfo)
        } else {
            val remoteUserInfo = profileRepository.fetchUserInfo()
            emit(remoteUserInfo)
        }

    }

}