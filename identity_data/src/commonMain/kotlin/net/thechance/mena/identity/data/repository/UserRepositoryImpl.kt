package net.thechance.mena.identity.data.repository

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import net.thechance.mena.identity.data.dataSource.local.database.dao.UserDao
import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto
import net.thechance.mena.identity.data.mapper.toDomain
import net.thechance.mena.identity.data.mapper.toEntity
import net.thechance.mena.identity.data.utils.getJson
import net.thechance.mena.identity.data.utils.safeWrapper
import net.thechance.mena.identity.domain.entity.User
import net.thechance.mena.identity.domain.repository.UserRepository


class UserRepositoryImpl(
    private val client: HttpClient,
    private val userDao: UserDao,
) : UserRepository {


    override suspend fun getUser(): Flow<User> {
       return userDao.getUser()
            .onStart {
                coroutineScope {
                    launch {
                        try {
                            val userInfo: ProfileResponseDto = client.getJson(path = PROFILE)
                            saveUserInfo(userInfo.toDomain())
                        } catch (e: Exception) {
                            println("Failed to fetch user: ${e.message}")
                        }
                    }
                }
            }
            .map { userEntity ->
                userEntity?.toDomain() ?: throw Exception("User Not Found")
            }
            .flowOn(Dispatchers.IO)
    }




     private suspend fun saveUserInfo(user: User) {
         userDao.upsert(user.toEntity())
    }


    companion object {
        const val PROFILE = "identity/profile/me"
    }


}