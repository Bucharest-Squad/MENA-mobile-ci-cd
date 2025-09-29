package net.thechance.mena.identity.data.repository

import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.thechance.mena.identity.data.dataSource.local.database.dao.UserDao
import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto
import net.thechance.mena.identity.data.mapper.toDomain
import net.thechance.mena.identity.data.mapper.toUser
import net.thechance.mena.identity.data.mapper.toUserEntity
import net.thechance.mena.identity.data.utils.getJson
import net.thechance.mena.identity.data.utils.safeWrapper
import net.thechance.mena.identity.domain.model.User
import net.thechance.mena.identity.domain.repository.UserRepository


class UserRepositoryImpl(
    private val client: HttpClient,
    private val userDao: UserDao,
) : UserRepository {


    override suspend fun getUser(): Flow<User> {
        withContext(Dispatchers.IO) {
            launch {
                safeWrapper {
                    val userInfo: ProfileResponseDto = client.getJson(path = PROFILE)
                    saveUserInfo(userInfo.toDomain())
                }
            }
        }
        return userDao.getUser().flowOn(Dispatchers.IO).map { userEntity -> userEntity.toUser() }
    }


    private suspend fun saveUserInfo(user: User) {
        with(Dispatchers.IO) {
            userDao.upsert(user.toUserEntity())
        }
    }


    companion object {
        const val PROFILE = "profile/me"
    }


}