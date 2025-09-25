package net.thechance.mena.identity.data.datasource.localDataSource

interface LocalDataSource {
    fun saveAccessToken(accessToken: String)

    fun getAccessToken(): String

    fun saveRefreshToken(refreshToken: String)

    fun getRefreshToken(): String

}