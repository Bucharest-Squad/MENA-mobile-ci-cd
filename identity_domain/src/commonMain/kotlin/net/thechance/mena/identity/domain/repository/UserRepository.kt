package net.thechance.mena.identity.domain.repository

import kotlinx.coroutines.flow.Flow
import net.thechance.mena.identity.domain.entity.User
import net.thechance.mena.identity.domain.model.AuthenticationTokens

interface UserRepository {
    suspend fun getUser(): Flow<User?>
    suspend fun updateUser(user: User, shouldUpdateImage: Boolean)
    suspend fun uploadUserProfileImage(imageByteArray: ByteArray?)
    suspend fun uploadUserProfileImageWithTokens(imageByteArray: ByteArray?, authTokens: AuthenticationTokens)
    suspend fun deleteUserProfileImage()
}