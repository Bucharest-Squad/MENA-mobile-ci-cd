package net.thechance.mena.identity.data.repository

import net.thechance.mena.identity.data.datasource.remoteDataSource.profile.ProfileRemoteDataSource
import net.thechance.mena.identity.data.mapper.toUserInfo
import net.thechance.mena.identity.domain.model.UserInfo
import net.thechance.mena.identity.domain.repository.ProfileRepository


class ProfileRepositoryImpl(
    private val remoteDataSource: ProfileRemoteDataSource,
    private val localDataSource: ProfileRemoteDataSource
): ProfileRepository {

    override suspend fun getUserInfo(): UserInfo {
        return remoteDataSource.getUserInfo().toUserInfo()
    }

}