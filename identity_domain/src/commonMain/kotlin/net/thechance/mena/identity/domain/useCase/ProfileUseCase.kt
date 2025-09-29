package net.thechance.mena.identity.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.thechance.mena.identity.domain.model.UserInfo
import net.thechance.mena.identity.domain.repository.UserRepository

class ProfileUseCase(
    private val userRepository: UserRepository

) {
    suspend fun getUserInfo(): Flow<UserInfo> = flow {

        val localUserInfo = userRepository.getUserInfoFromLocal()
        if (localUserInfo != null) {
            emit(localUserInfo)
        } else {
            val remoteUserInfo = userRepository.fetchUserInfo()
            emit(remoteUserInfo)
        }

    }

}