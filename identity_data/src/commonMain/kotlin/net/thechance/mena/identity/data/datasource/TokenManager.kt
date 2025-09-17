package net.thechance.mena.identity.data.datasource

import com.russhwolf.settings.Settings
import net.thechance.mena.identity.data.datasource.DataStoreConstants.ACCESS_TOKEN
import net.thechance.mena.identity.data.datasource.DataStoreConstants.REFRESH_TOKEN

class TokenManager(
    private val settings: Settings
) :LocalDataSource{
    override fun saveAccessToken(accessToken: String) {
        settings.putString(ACCESS_TOKEN, accessToken)
    }

    override fun getAccessToken(): String {
        return settings.getString(ACCESS_TOKEN, "")
    }

    override fun saveRefreshToken(refreshToken: String) {
        settings.putString(REFRESH_TOKEN, refreshToken)
    }

    override fun getRefreshToken(): String {
        return settings.getString(REFRESH_TOKEN, "")
    }

}
