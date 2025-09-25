package net.thechance.mena.identity.data.datasource.localDataSource

import net.thechance.mena.identity.domain.model.UserInfo

interface UserLocalDataSource {
    fun saveAccessToken(accessToken: String)

    fun getAccessToken(): String

    fun saveRefreshToken(refreshToken: String)

    fun getRefreshToken(): String

    fun saveUserInfo(userInfo: UserInfo)

    fun getUserInfo(): UserInfo?
}