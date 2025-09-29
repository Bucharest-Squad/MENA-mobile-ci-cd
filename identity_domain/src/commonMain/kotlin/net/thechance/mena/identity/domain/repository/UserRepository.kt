package net.thechance.mena.identity.domain.repository

import kotlinx.coroutines.flow.Flow
import net.thechance.mena.identity.domain.model.User

interface UserRepository {

     suspend fun getUser(): Flow<User>


}