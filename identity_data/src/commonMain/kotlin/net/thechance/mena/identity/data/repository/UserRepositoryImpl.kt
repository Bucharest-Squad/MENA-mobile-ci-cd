package net.thechance.mena.identity.data.repository

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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


    val handler = CoroutineExceptionHandler { _, throwable ->
        println("Exception was caught: ${throwable.message}")
    }

    override suspend fun getUser(): Flow<User> {
        withContext(Dispatchers.IO ) {
            supervisorScope {
                launch(handler) {
                    safeWrapper {
                        val userInfo: ProfileResponseDto = client.getJson(path = PROFILE)
                        saveUserInfo(userInfo.toDomain())
                    }
                }
            }
        }
        return userDao.getUser()
            .map { userEntity -> userEntity?.toDomain() ?: throw Exception("User Not Found") }
            .flowOn(Dispatchers.IO)

    }


     private suspend fun saveUserInfo(user: User) {
        withContext(Dispatchers.IO) {
            userDao.upsert(user.toEntity())
        }
    }


    companion object {
        const val PROFILE = "identity/profile/me"
    }


}