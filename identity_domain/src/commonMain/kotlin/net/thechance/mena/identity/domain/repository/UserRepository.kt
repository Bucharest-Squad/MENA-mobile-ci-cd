package net.thechance.mena.identity.domain.repository

import kotlinx.coroutines.flow.Flow
import net.thechance.mena.identity.domain.model.User

interface UserRepository {

     fun getUser(): Flow<User>


}