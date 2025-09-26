package net.thechance.mena.identity.data.repository

import kotlinx.coroutines.flow.Flow
import net.thechance.mena.identity.data.datasource.localDataSource.UserLocalDataSource
import net.thechance.mena.identity.data.datasource.remoteDataSource.UserRemoteDataSource
import net.thechance.mena.identity.data.mapper.toUserInfo
import net.thechance.mena.identity.data.utils.safeWrapper
import net.thechance.mena.identity.domain.model.UserInfo
import net.thechance.mena.identity.domain.repository.ProfileRepository


class ProfileRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource
): ProfileRepository {


    override suspend fun fetchUserInfo(): UserInfo {
        val userInfo = safeWrapper {
            remoteDataSource.getUserInfo().toUserInfo()
        }
        saveUserInfo(userInfo)
        return userInfo
    }

    override suspend fun getUserInfoFromLocal(): UserInfo?{
        return localDataSource.getUserInfo()
    }

    private fun saveUserInfo(userInfo: UserInfo){
        localDataSource.saveUserInfo(userInfo)
    }

}